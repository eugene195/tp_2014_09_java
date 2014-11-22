package global.engine;

/**
 * Created by max on 25.10.14.
 */
public enum Color {
    BLACK,
    ORANGE,
    WHITE,
    RED,
    VIOLET,
    GREEN,
    BLUE,
    CYAN,
    BROWN,
    UNKNOWN;

    public static Color getColor(int I) {
        return Color.values()[I];
    }
}
