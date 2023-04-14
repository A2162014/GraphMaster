package graphmaster.grapher.expressions;

/**
 * The Log class represents a logarithmic expression with a specified base.
 */
public class Log extends Binary {
    /**
     * Constructs a Log object with a given Quantity and base.
     *
     * @param x    the Quantity to be logged
     * @param base the base of the logarithmic expression
     */
    public Log(Quantity x, Quantity base) {
        super(x, base);
    }

    @Override
    public double getValue() {
        double val1 = realValue(q1);
        double val2 = realValue(q2);
        return StrictMath.log(val1) / StrictMath.log(val2);
    }
}
