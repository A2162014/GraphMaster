package graphmaster.grapher.expressions;

/**
 * This class represents the mathematical expression of the difference between two quantities.
 */
public class Difference extends Binary {

    /**
     * Constructs a new Difference object with two quantities.
     *
     * @param q1 the first quantity
     * @param q2 the second quantity
     */
    public Difference(Quantity q1, Quantity q2) {
        super(q1, q2);
    }

    @Override
    public double getValue() {
        double val1 = realValue(q1);
        double val2 = realValue(q2);
        return val1 - val2;
    }
}
