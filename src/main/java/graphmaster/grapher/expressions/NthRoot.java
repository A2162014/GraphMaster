package graphmaster.grapher.expressions;

/**
 * Represents a mathematical expression that calculates the nth root of a quantity.
 */
public class NthRoot extends Binary {

    /**
     * Creates a new NthRoot expression with the specified quantities.
     *
     * @param q    the quantity to take the nth root of
     * @param root the root to use
     */
    public NthRoot(Quantity q, Quantity root) {
        super(q, root);
    }

    @Override
    public double getValue() {
        double val1 = Quantity.realValue(q1);
        double val2 = Quantity.realValue(q2);
        return Math.pow(val1, 1.0 / val2);
    }

}
