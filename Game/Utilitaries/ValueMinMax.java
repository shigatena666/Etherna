package Game.Utilitaries;

/**
 * Object that allows you to have a "slider" in which you ca move the value and it will never go out of the min, max.
 */
public class ValueMinMax {
    private int min;
    private int max;
    private int value;

    //region Constructors

    /**
     * Default ctor.
     * Creates an instance with 0 as min, 100 as max and 0 as start value.
     */
    public ValueMinMax() {
        this.min = 0;
        this.max = 100;
        this.value = 0;
    }

    /**
     * Params ctor.
     * Creates an instance with the given parameters.
     *
     * @param MIN   The minimum the value can reach.
     * @param MAX   The maximum the value can reach.
     * @param value The current value.
     */
    public ValueMinMax(int MIN, int MAX, int value) {
        this.min = MIN;
        this.max = MAX;
        this.value = value;
    }

    /**
     * Copy ctor.
     *
     * @param valueMinMax Copy the parameters of an instance of ValueMinMax.
     */
    public ValueMinMax(ValueMinMax valueMinMax) {
        this(valueMinMax.getMin(), valueMinMax.getMax(), valueMinMax.getValue());
    }

    //endregion

    //region Accessors

    /**
     * @return The minimum value.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min The new value to give to the min value.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return The maximum value.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max The new value to give to the max value.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return The current value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Changes the current value by an integer if it is in the range of [min - max].
     *
     * @param value The value to give to the current value.
     */
    public void setValue(int value) {
        if (value > max )
            value = max;
        else if(value < min)
            value = min;
        this.value = value;
    }

    //endregion

    @Override
    public String toString() {
        return "ValueMinMax{" +
                "min=" + min +
                ", max=" + max +
                ", value=" + value +
                '}';
    }
}