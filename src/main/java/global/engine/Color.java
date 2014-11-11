package global.engine;

/**
 * Created by max on 25.10.14.
 */
public enum Color {
    UNKNOWN,
    BLACK,
    WHITE,
    RED,
    GREEN,
    BLUE,
    VIOLET,
    ORANGE,
    CYAN,
    BROWN;

    public static Color getColor(int I) {
        return Color.values()[I];
    }
}
