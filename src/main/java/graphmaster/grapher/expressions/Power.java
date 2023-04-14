package graphmaster.grapher.expressions;

/**
 * The Power class represents a binary expression that raises the first quantity to the power of the second quantity.
 */
public class Power extends Binary {

    /**
     * Constructs a Power object with the given quantities as the base and exponent.
     *
     * @param x   the base quantity.
     * @param exp the exponent quantity.
     */
    public Power(Quantity x, Quantity exp) {
        super(x, exp);
    }

    @Override
    public double getValue() {
        double val1 = realValue(q1);
        double val2 = realValue(q2);
        return StrictMath.pow(val1, val2);
    }
}
