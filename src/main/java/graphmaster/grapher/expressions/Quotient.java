package graphmaster.grapher.expressions;

/**
 * The Quotient class represents a binary operation that divides two quantities.
 */
public class Quotient extends Binary {

    /**
     * Constructs a Quotient object with the specified quantities.
     *
     * @param q1 the first quantity to divide.
     * @param q2 the second quantity to divide.
     */
    public Quotient(Quantity q1, Quantity q2) {
        super(q1, q2);
    }

    @Override
    public double getValue() {
        double val1 = realValue(q1);
        double val2 = realValue(q2);
        return val1 / val2;
    }
}
