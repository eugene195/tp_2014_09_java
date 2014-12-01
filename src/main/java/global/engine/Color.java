package global.engine;

/**
 * Created by max on 25.10.14.
 */
public enum Color {
    BLUE,
    ORANGE,
    WHITE,
    RED,
    BLACK,
    VIOLET,
    GREEN,
    CYAN,
    BROWN,
    UNKNOWN;

    public static Color getColor(int I) {
        return Color.values()[I];
    }
}
