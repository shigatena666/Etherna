package Game.Utilitaries;

import java.util.Random;

public class Position {
    private int x;
    private int y;

    //region Constructors

    /**
     * Default ctor.
     * Creates a position with x = 0, y = 0.
     */
    public Position() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Params ctor.
     * Creates a position with x, y values.
     *
     * @param x Give the x value to the position.
     * @param y Give the y value to the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy ctor.
     * Copy the parameters of an existing position.
     *
     * @param position The instance to copy.
     */
    public Position(Position position) {
        this(position.getX(), position.getY());
    }

    //endregion

    //region Accessors

    /**
     * @return The x value in the position.
     */
    public int getX() {
        return x;
    }

    /**
     * @param x The value which will be given to x (always positive or defaultly set to 0).
     */
    public void setX(int x) {
        this.x = Math.max(x, 0);
    }

    /**
     * @return The y value in the position.
     */
    public int getY() {
        return y;
    }

    /**
     * @param y The value which will be given to y (always positive or defaultly set to 0).
     */
    public void setY(int y) {
        this.y = Math.max(y, 0);
    }

    /**
     * Move the position by 1 in the upper direction.
     */
    public void moveUp() {
        this.y--;
    }

    /**
     * Move the position by 1 in the lower direction.
     */
    public void moveDown() {
        this.y++;
    }

    /**
     * Move the position by 1 in the left direction.
     */
    public void moveLeft() {
        this.x--;
    }

    /**
     * Move the position by 1 in the right direction.
     */
    public void moveRight() {
        this.x++;
    }

    /**
     * @param x The value which will be given to x (always positive or defaultly set to 0).
     * @param y The value which will be given to y (always positive or defaultly set to 0).
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //endregion

    //region Methods

    /**
     * Randomize a new position on the current instance.
     *
     * @param range The range in which the x's and y's value will be chosen.
     */
    public void randomPos(int range) {
        Random r = new Random();
        set(r.nextInt(range), r.nextInt(range));
    }

    //endregion

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
