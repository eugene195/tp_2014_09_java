package global.engine;

import global.GameMechanics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by max on 25.10.14.
 */
public class Engine {
    private int width, height;
    private int speed;

    private final ArrayList<Cell> cells = new ArrayList<>();
    private final ArrayList<Snake> snakes = new ArrayList<>();

    private GameMechanics mechanic;

    public Engine(GameMechanics mechanic, int width, int height, int speed, int snakesCnt) {
        this.mechanic = mechanic;

        this.width = width;
        this.height = height;
        this.speed = speed;

        for (int I = 0; I < height; I++)
            for (int J = 0; J < width; J++) {
                cells.add(new Cell(J, I));
            }

        for (int I = 0; I < snakesCnt; I++) {
            snakes.add(new Snake(Color.BLACK));
        }

        this.mechanic.sendToClients("startGame", this.getStartData(), this);
    }

    private Map<String, Object> getStartData() {
        Map<String, Object> data = new HashMap<>();
        data.put("height", height);
        data.put("width", width);
        data.put("speed", speed);

        // TODO: snakes info

        return data;
    }

    public void timerEvent() {
        Location cur;

        for (Snake snake: snakes) {
            snake.move(width-1, height-1);
            cur = snake.getLocation();
            snake.grasp(getCell(cur));
        }

        mechanic.sendToClients("tick", this.getTickData(), this);

        checkGameEnd();
    }


    private Map<String, Object> getTickData() {
        Map<String, Object> data = new HashMap<>();

        // TODO: snakes info

        return data;
    }

    public void handleKey(int snakeId, Direct direct) {
        snakes.get(snakeId).turn(direct);
    }


    private Cell getCell(Location loc) {
        int pos = loc.X + loc.Y * width;
        return this.cells.get(pos);
    }

    private boolean collisionFor(int J) {
        Snake snakej = snakes.get(J);
        Location aliveLoc = snakej.getLocation();

        for (int I = 0; I < snakes.size(); I++)
            if (I != J && snakes.get(I).getLocation().equals(aliveLoc)) {
                snakej.setAlive(false);

                // TODO: COLLISION

                return true;
            }

        return false;
    }

    private void checkGameEnd() {
        int alives = 0;
        for (int I = 0; I < snakes.size(); I++)
            if (snakes.get(I).isAlive() && ! collisionFor(I))
                alives++;

        if (alives <= 1)
            endGame();
    }

    private void endGame() {
        Map<String, Object> data = new HashMap<>();

        // TODO: snakes info

        mechanic.sendToClients("endGame", data, this);
    }
}
