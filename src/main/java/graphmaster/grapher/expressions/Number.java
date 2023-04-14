package graphmaster.grapher.expressions;

/**
 * The Number class represents a numerical value as a Quantity.
 */
public class Number extends Quantity {

    double num;

    /**
     * Constructs a Number instance with the specified numerical value.
     *
     * @param num The numerical value to be represented by this Number instance.
     */
    public Number(double num) {
        this.num = num;
    }

    @Override
    public double getValue() {
        return num;
    }
}
