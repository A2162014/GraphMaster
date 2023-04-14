package graphmaster.grapher.expressions;

/**
 * Modulo represents the modulo operation between two Quantity objects.
 */
public class Modulo extends Binary {

    /**
     * Constructs a Modulo object with two Quantity operands.
     *
     * @param q1 the first Quantity operand
     * @param q2 the second Quantity operand
     */
    public Modulo(Quantity q1, Quantity q2) {
        super(q1, q2);
    }

    @Override
    public double getValue() {
        double val1 = realValue(q1);
        double val2 = realValue(q2);
        return val1 % val2;
    }
}
