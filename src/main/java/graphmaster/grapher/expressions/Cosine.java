package graphmaster.grapher.expressions;

/**
 * The Cosine class represents a cosine expression.
 */
public class Cosine extends Unary {

    /**
     * Constructs a new Cosine expression with the specified operand.
     *
     * @param q the operand of the cosine expression
     */
    public Cosine(final Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        final double val = Quantity.realValue(this.q);
        return StrictMath.cos(val);
    }
}
