package graphmaster.grapher.expressions;

/**
 * The Product class represents a binary operation that multiplying two Quantity objects.
 */
public class Product extends Binary {

    /**
     * Constructs a Product object with two Quantity operands.
     *
     * @param q1 the first Quantity operand
     * @param q2 the second Quantity operand
     */
    public Product(Quantity q1, Quantity q2) {
        super(q1, q2);
    }

    @Override
    public double getValue() {
        double val1 = realValue(q1);
        double val2 = realValue(q2);
        return val1 * val2;
    }
}
