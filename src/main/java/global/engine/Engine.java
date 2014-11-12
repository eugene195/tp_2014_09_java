package global.engine;

import global.GameMechanics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by max on 25.10.14.
 */
public class Engine {

    private boolean launched;

    private int width, height;
    private int speed;

    private final ArrayList<Cell> cells = new ArrayList<>();
    private final ArrayList<Snake> snakes = new ArrayList<>();

    private GameMechanics mechanic;

    //---------------------------------------------------------------------------------------------------

    public Engine(GameMechanics mechanic, int width, int height, int speed, int snakesCnt) {
        this.launched = false;
        this.mechanic = mechanic;
        this.speed = speed;

        this.generateField(width, height);
        this.generateSnakes(snakesCnt);
    }

    public void launch() {
        this.launched = true;
        this.mechanic.sendToClients("startGame", this.getStartData(), this);
    }

    private void generateField(int width, int height) {
        this.width = width;
        this.height = height;

        for (int I = 0; I < height; I++)
            for (int J = 0; J < width; J++) {
                cells.add(new Cell(J, I));
            }
    }

    private void generateSnakes(int snakesCnt) {
        for (int I = 0; I < snakesCnt; I++) {
            snakes.add(new Snake(I, Color.getColor(I)));
        }
    }

    private Map<String, Object> getStartData() {
        Map<String, Object> data = new HashMap<>();
        data.put("height", height);
        data.put("width", width);
        data.put("speed", speed);

        ArrayList<Map<String, Object>> snakeInfo = new ArrayList<>();
        Map<String, Object> block;
        Location pos;

        for (Snake snake : snakes) {
            block = new HashMap<>();
            block.put("snakeId", snake.getId());
            block.put("color", snake.getColor().name());

            pos = snake.getLocation();
            block.put("posX", pos.X);
            block.put("posY", pos.Y);
            snakeInfo.add(block);
        }

        data.put("snakes", snakeInfo);

        return data;
    }

    public void timerEvent() {
        if (! launched) return;

        boolean justKilled;
        Cell cell;

        for (Snake snake: snakes) {
            snake.move(width-1, height-1);
            cell = getCell(snake.getLocation());
            justKilled = snake.grasp(cell);

            if (justKilled) {
                this.sendCollision(snake.getId(), cell.getOwner());
            }
        }

        mechanic.sendToClients("tick", this.getTickData(), this);

        this.checkGameEnd();
    }


    private Map<String, Object> getTickData() {
        Map<String, Object> data = new HashMap<>();

        ArrayList<Map<String, Object>> snakeInfo = new ArrayList<>();
        Map<String, Object> block;
        Location pos;

        for (Snake snake : snakes) {
            block = new HashMap<>();
            pos = snake.getLocation();
            block.put("snakeId", snake.getId());
            block.put("newX", pos.X);
            block.put("newY", pos.Y);
            snakeInfo.add(block);
        }

        data.put("snakes", snakeInfo);
        return data;
    }

    public void handleKey(int snakeId, Direct direct) {
        snakes.get(snakeId).turn(direct);
    }


    private Cell getCell(Location loc) {
        int pos = loc.X + loc.Y * width;
        return this.cells.get(pos);
    }

    private void sendCollision(int snakeId, int withId) {
        Map<String, Object> data = new HashMap<>();
        data.put("snakeId", snakeId);
        data.put("withId", withId);
        mechanic.sendToClients("endGame", data, this);
    }

    private boolean collisionFor(int J) {
        Snake snakej = snakes.get(J);
        Location aliveLoc = snakej.getLocation();

        for (int I = 0; I < snakes.size(); I++)
            if (I != J && snakes.get(I).getLocation().equals(aliveLoc)) {

                snakej.setAlive(false);
                this.sendCollision(J, I);
                return true;
            }

        return false;
    }

    private void checkGameEnd() {
        int alives = 0;
        for (Snake snake : snakes)
            if (snake.isAlive() && ! collisionFor(snake.getId()))
                alives++;

        if (alives <= 1)
            this.endGame();
    }

    private int findWinner() {
        for (Snake snake : snakes) {
            if (snake.isAlive())
                return snake.getId();
        }
        return -1;
    }

    private void endGame() {
        Map<String, Object> data = new HashMap<>();

        int winner = findWinner();
        data.put("winner", winner);
        data.put("color", snakes.get(winner).getColor().name());

        mechanic.sendToClients("endGame", data, this);
        mechanic.endGame(this);
    }

    public void execAction(String action, Map<String, Object> data) {
        if (action.equals("handleKey")) {
            String dirStr = (String) data.get("direct");
            Direct dir = Direct.valueOf(dirStr);
            int snakeId = (Integer) data.get("snakeId");

            this.handleKey(snakeId, dir);
        }
    }
}
