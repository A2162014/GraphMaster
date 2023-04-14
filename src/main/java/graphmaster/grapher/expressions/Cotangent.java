package graphmaster.grapher.expressions;

/**
 * The Cotangent class represents a mathematical expression that calculates the cotangent of a quantity.
 */
public class Cotangent extends Unary {

    /**
     * Constructs a new Cotangent expression with the specified quantity.
     *
     * @param q the quantity to use in the expression
     */
    public Cotangent(final Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        final double val = Quantity.realValue(this.q);
        return 1.0 / StrictMath.tan(val);
    }
}
