package graphmaster.grapher.expressions;

/**
 * This class represents a binary sum expression between two quantities.
 */
public class Sum extends Binary {

    /**
     * Constructs a Sum object with two quantities.
     *
     * @param q1 The first quantity to be added.
     * @param q2 The second quantity to be added.
     */
    public Sum(Quantity q1, Quantity q2) {
        super(q1, q2);
    }

    @Override
    public double getValue() {
        double val1 = realValue(q1);
        double val2 = realValue(q2);
        return val1 + val2;
    }
}
