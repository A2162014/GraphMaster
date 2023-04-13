package graphmaster.grapher.expressions;

/**
 * The Cosecant class represents the cosecant function in mathematics.
 * It's a subclass of Unary and takes a Quantity object as its input.
 */
public class Cosecant extends Unary {

    /**
     * Constructs a Cosecant object with the specified Quantity input.
     *
     * @param q the input Quantity object.
     */
    public Cosecant(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = realValue(q);
        return 1.0 / Math.sin(val);
    }
}
