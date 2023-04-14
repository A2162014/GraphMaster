package graphmaster.grapher.expressions;

/**
 * The Floor class represents an expression
 * that computes the largest integer value less than or equal to the value of its operand.
 */
public class Floor extends Unary {

    /**
     * Constructs a new Floor expression with the specified operand.
     *
     * @param q the operand of the Floor expression
     */
    public Floor(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = Quantity.realValue(q);
        return Math.floor(val);
    }
}
