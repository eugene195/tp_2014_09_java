package global.engine;

/**
 * Created by max on 25.10.14.
 */
public enum Color {
    WHITE,
    RED,
    BLACK,
    GREEN,
    BLUE,
    VIOLET,
    ORANGE,
    CYAN,
    BROWN,
    UNKNOWN;

    public static Color getColor(int I) {
        return Color.values()[I];
    }
}
