package global.engine;

import global.GameMechanics;
import global.mechanic.GameSession;

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

    public Engine(GameMechanics mechanic, int width, int height, int speed) {
        this.launched = false;
        this.mechanic = mechanic;
        this.speed = speed;

        this.generateField(width, height);
    }

    public void launch() {
        // this.launched = true;
        this.mechanic.sendToClients("adjustGame", this.getStartData(), this);
    }

    private void generateField(int width, int height) {
        this.width = width;
        this.height = height;

        for (int I = 0; I < height; I++)
            for (int J = 0; J < width; J++) {
                cells.add(new Cell(J, I));
            }
    }

    public void generateSnakes(GameSession session) {
        int playersCnt = session.getPlayersCnt();
        int distX = width * 6 / 10,
            distY = height * 6 / 10;
        double angle = 2*Math.PI / playersCnt;

        Location center = new Location(width / 2, height / 2);

        int I = 0;
        for (String playerName : session.getPlayers()) {
            long id = session.getSnakeId(playerName);

            double posX = distX*Math.cos(angle*I),
                   posY = distY*Math.sin(angle*I);

            Location pos = new Location((int) posX, (int) posY);
            Direct direct = pos.getDirect(center);

            snakes.add(new Snake(id, pos, direct, Color.getColor(I)));
            I++;
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
        Direct direct;

        for (Snake snake : snakes) {
            block = new HashMap<>();
            block.put("snakeId", snake.getId());
            block.put("color", snake.getColor().name());

            pos = snake.getLocation();
            direct = snake.getDirect();

            block.put("posX", pos.X);
            block.put("posY", pos.Y);
            block.put("direct", direct.name());
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

    private Snake getSnakeById(long snakeId) {
        for (Snake snake : snakes) {
            if (snake.getId() == snakeId)
                return snake;
        }
        return null;
    }

    public void handleKey(long snakeId, Direct direct) {
        Snake snake = getSnakeById(snakeId);
        if (snake != null) {
            snake.turn(direct);
        }
    }


    private Cell getCell(Location loc) {
        int pos = loc.X + loc.Y * width;
        return this.cells.get(pos);
    }

    private void sendCollision(long snakeId, long withId) {
        Map<String, Object> data = new HashMap<>();
        data.put("snakeId", snakeId);
        data.put("withId", withId);
        mechanic.sendToClients("endGame", data, this);
    }

    private boolean collisionFor(Snake snakej) {
        int J = snakes.indexOf(snakej);
        Location aliveLoc = snakej.getLocation();

        for (Snake snake : snakes)
            if (snake != snakej && snake.getLocation().equals(aliveLoc)) {
                snakej.setAlive(false);
                this.sendCollision(snakej.getId(), snake.getId());
                return true;
            }

        return false;
    }

    private void checkGameEnd() {
        int alives = 0;
        Snake winner = null;
        for (Snake snake : snakes)
            if (snake.isAlive() && ! collisionFor(snake)) {
                alives++;
                winner = snake;
            }

        if (alives <= 1)
            this.endGame(winner);
    }

    private void endGame(Snake winner) {
        Map<String, Object> data = new HashMap<>();

        if (winner != null) {
            data.put("winner", winner.getId());
            data.put("color", winner.getColor().name());
        } else {
            data.put("winner", -1);
            data.put("color", "");
        }

        mechanic.sendToClients("endGame", data, this);
        mechanic.endGame(this);
    }

    public void execAction(String action, Map<String, Object> data) {
        if (action.equals("handleKey")) {
            String dirStr = (String) data.get("direct");
            Direct dir = Direct.valueOf(dirStr);
            long snakeId = (Integer) data.get("snakeId");

            this.handleKey(snakeId, dir);
        }
    }
}
