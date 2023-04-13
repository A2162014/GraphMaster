package graphmaster.grapher.expressions;

/**
 * The Quantity class represents a quantity with a numeric value.
 */
public abstract class Quantity {
    protected Quantity() {
    }

    /**
     * Returns the real value of the given Quantity.
     *
     * @param q the Quantity to get the real value of
     * @return the real value of the Quantity, or Double. NaN if the Quantity is null.
     */
    public static double realValue(Quantity q) {
        return null != q ? q.getValue() : Double.NaN;
    }

    /**
     * Abstract method that returns the value of the Quantity.
     *
     * @return the value of the Quantity
     */
    public abstract double getValue();
}
