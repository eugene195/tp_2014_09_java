package global.engine;

/**
 * Created by max on 25.10.14.
 */
public class Cell {
    private Location loc;
    private long ownerId;
    private boolean _isGrasped;

    public Cell() {
        this(0, 0);
    }

    public Cell(int X, int Y) {
        this.loc = new Location(X, Y);
        this.ownerId = -1;
        this._isGrasped = false;
    }

    public void set(int X, int Y) {
        this.loc.X = X;
        this.loc.Y = Y;
    }

    public void setOwner(long newOwner) {
        this.ownerId = newOwner;
        this._isGrasped = true;
    }

    public long getOwner() {
        return this.ownerId;
    }

    public boolean isGrasped() {
        return this._isGrasped;
    }
}
