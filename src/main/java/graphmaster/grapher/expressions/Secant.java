package graphmaster.grapher.expressions;

/**
 * The Secant class represents a mathematical expression for the secant function of a given Quantity.
 */
public class Secant extends Unary {

    /**
     * Constructs a Secant object with the specified Quantity.
     *
     * @param q the Quantity for which to evaluate the secant function.
     */
    public Secant(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = realValue(q);
        return 1.0 / StrictMath.cos(val);
    }
}
