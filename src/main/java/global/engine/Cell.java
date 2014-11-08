package global.engine;

/**
 * Created by max on 25.10.14.
 */
public class Cell {
    private Location loc;
    private Color color;
    private boolean _isGrasped;

    public Cell() {
        this(0, 0);
    }

    public Cell(int X, int Y) {
        this.loc = new Location(X, Y);
        this.color = Color.WHITE;
        this._isGrasped = false;
    }

    public void set(int X, int Y) {
        this.loc.X = X;
        this.loc.Y = Y;
    }

    public void setColor(Color new_color) {
        this.color = new_color;
        this._isGrasped = true;
    }

    public boolean isGrasped() {
        return this._isGrasped;
    }
}
