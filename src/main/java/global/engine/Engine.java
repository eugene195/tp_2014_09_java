package global.engine;

import java.util.ArrayList;

/**
 * Created by max on 25.10.14.
 */
public class Engine {
    private int width, height;
    private int speed;

    private ArrayList<Cell> cells;
    private ArrayList<Snake> snakes;

    public Engine(int width, int height, int speed, int snakesCnt) {
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

        // TODO: START_GAME
    }

    public void timerEvent() {
        Location cur;

        for (Snake snake : snakes) {
            snake.move(width-1, height-1);
            cur = snake.getLocation();
            snake.grasp(getCell(cur));
        }

        // TODO: TICK

        checkGameEnd();
    }

    public void handleKey(int snakeId, Direct direct) {
        snakes.get(snakeId).turn(direct);
    }


    private Cell getCell(Location loc) {
        int pos = loc.X + loc.Y * width;
        return this.cells.get(pos);
    }

    private boolean collisionFor(int J) {
        Location aliveLoc = snakes.get(J).getLocation();

        for (int I = 0; I < snakes.size(); I++)
            if (I != J && snakes.get(I).getLocation() == aliveLoc) {
                snakes.get(J).setAlive(false);

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
        String message = "";
        for (Snake snake : snakes)
            message += snake.getGameResult();

        // TODO: END_GAME
    }
}
