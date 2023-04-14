package graphmaster.grapher.expressions;

/**
 * Represents the tangent of a Quantity.
 */
public class Tangent extends Unary {

    /**
     * Constructs a Tangent object with the given Quantity.
     *
     * @param q the Quantity to apply the tangent function to.
     */
    public Tangent(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = Quantity.realValue(q);
        return StrictMath.tan(val);
    }
}
