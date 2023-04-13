package graphmaster.grapher.expressions;

/**
 * The Unary class represents a mathematical expression that operates on a single quantity.
 */
public abstract class Unary extends Quantity {

    protected Quantity q;

    /**
     * Constructs an Unary object with the given quantity.
     *
     * @param q the quantity to be used in the expression
     */
    public Unary(Quantity q) {
        this.q = q;
    }

    @Override
    public abstract double getValue();
}
