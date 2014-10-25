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
