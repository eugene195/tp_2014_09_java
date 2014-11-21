package global.engine;

/**
 * Created by max on 25.10.14.
 */
public enum Direct {
    UNKNOWN,
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Direct parse(int dx, int dy) {
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx >= 0) return RIGHT;
            else return LEFT;
        }
        else {
            if (dy >= 0) return UP;
            else return DOWN;
        }
    }

    public Direct invert() {
        switch (this) {
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            case UP: return DOWN;
            case DOWN: return UP;
            default: return UNKNOWN;
        }
    }
}
