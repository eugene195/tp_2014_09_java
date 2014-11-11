package global.engine;

/**
 * Created by max on 25.10.14.
 */
public class Snake {
    private int snakeId;
    private Location loc;
    private Color color;
    private Direct curDirect;
    private boolean _isAlive;

    public Snake() {
        this(-1, 0, 0, Direct.RIGHT, Color.BLACK);
    }

    public Snake(int snakeId, Color color) {
        this(snakeId, 0, 0, Direct.RIGHT, color);
    }

    public Snake(int snakeId, int X, int Y, Direct direct, Color color) {
        this.snakeId = snakeId;
        this.loc = new Location(X, Y);
        this.color = color;
        this.curDirect = direct;
        this._isAlive = true;
    }

    public void move(int rightX, int rightY) {
        if (! _isAlive)
            return;

        switch (curDirect) {
            case LEFT: loc.X--; break;
            case RIGHT: loc.X++; break;
            case UP: loc.Y++; break;
            case DOWN: loc.Y--; break;
            default: return;
        }

        loc.circleX(0, rightX);
        loc.circleY(0, rightY);
    }

    public void turn(Direct direct) {
        if (! _isAlive)
            return;

        if (curDirect != direct.invert())
            curDirect = direct;
    }

    /**
     * This function is for grasping of new cell
     * @param cell new cell to grasp
     * @return justKilled
     */
    public boolean grasp(Cell cell) {
        if (! _isAlive)
            return false;

        if (cell.isGrasped()) {
            _isAlive = false;
            return true;
        }

        cell.setOwner(snakeId);
        return false;
    }

    public int getId() { return snakeId; }

    public Location getLocation() {
        return loc;
    }

    public String getGameResult() {
        if (_isAlive)
            return "\n" + color.name() + " is a winner!!\n\n";
        return color.name() + " is a loser!!\n";
    }

    public void setAlive(boolean flag) {
        _isAlive = flag;
    }

    public boolean isAlive() {
        return _isAlive;
    }

    public Color getColor() {
        return color;
    }
}
