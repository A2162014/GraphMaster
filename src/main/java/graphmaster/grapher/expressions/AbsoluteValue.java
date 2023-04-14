package graphmaster.grapher.expressions;

/**
 * The AbsoluteValue class represents an expression that evaluates to the absolute value of its operand.
 */
public class AbsoluteValue extends Unary {

    /**
     * Constructs an AbsoluteValue object with the specified operand.
     *
     * @param q the operand of the AbsoluteValue expression
     */
    public AbsoluteValue(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        final double val = Quantity.realValue(this.q);
        return Math.abs(val);
    }
}
