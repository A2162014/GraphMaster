package graphmaster.grapher.expressions;

/**
 * Represents the sine function.
 */
public class Sine extends Unary {

    /**
     * Constructs a new Sine object with the specified quantity.
     *
     * @param q the Quantity object to be used in the Sine operation
     */
    public Sine(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = Quantity.realValue(q);
        return StrictMath.sin(val);
    }
}
