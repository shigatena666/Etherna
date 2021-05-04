package Game.Utilitaries;

public class Range {
    private int horizontal;
    private int vertical;
    private int diagonal;
    private int view;

    //region Constructors

    /**
     * Default ctor.
     * Creates a range of 1 with 0 as diagonal.
     */
    public Range() {
        this.horizontal = 1;
        this.vertical = 1;
        this.diagonal = 0;
        this.view = 3;
    }

    /**
     * Params ctor.
     *
     * @param horizontal Horizontal range.
     * @param vertical   Vertical range.
     * @param diagonal   Diagonal range.
     */
    public Range(int horizontal, int vertical, int diagonal, int view) {
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.diagonal = diagonal;
        this.view = view; //Distance de visuel de la map par joueur
    }

    /**
     * Copy ctor.
     *
     * @param range The range instance to copy.
     */
    public Range(Range range) {
        this(range.getHorizontal(), range.getVertical(), range.getDiagonal(), range.getView());
    }

    //endregion

    //region Accessors

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public int getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(int diagonal) {
        this.diagonal = diagonal;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    //endregion

    @Override
    public String toString() {
        return "Range{" +
                "horizontal=" + horizontal +
                ", vertical=" + vertical +
                ", diagonal=" + diagonal +
                ", view=" + view +
                '}';
    }
}
