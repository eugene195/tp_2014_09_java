package global.engine;

/**
 * Created by max on 25.10.14.
 */
public class Location {
    public int X;
    public int Y;

    public Location() {
        X = Y = 0;
    }

    public Location(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public void circleX(int left, int right) {
        if (X < left)
            X = right;
        else if (X > right)
            X = left;
    }

    public void circleY(int left, int right) {
        if (Y < left)
            Y = right;
        else if (Y > right)
            Y = left;
    }

    public boolean equals(final Location loc) {
        return (X == loc.X && Y == loc.Y);
    }
}
