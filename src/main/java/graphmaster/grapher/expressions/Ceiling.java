package graphmaster.grapher.expressions;

/**
 * The Ceiling class represents the mathematical operation of rounding a given Quantity up to the nearest integer.
 */
public class Ceiling extends Unary {

    /**
     * Constructs a Ceiling object with the given Quantity.
     *
     * @param q the Quantity to round up.
     */
    public Ceiling(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = realValue(q);
        return Math.ceil(val);
    }
}
