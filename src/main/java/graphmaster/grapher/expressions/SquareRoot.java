package graphmaster.grapher.expressions;

/**
 * Represents a square root operation on a quantity.
 */
public class SquareRoot extends Unary {

    /**
     * Constructs a new SquareRoot object with the specified quantity.
     *
     * @param q the quantity to apply the square root operation to
     */
    public SquareRoot(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = realValue(q);
        return Math.sqrt(val);
    }

}
