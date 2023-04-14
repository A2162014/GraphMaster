package graphmaster.grapher.expressions;

/**
 * A class representing a mathematical function with three variables: x, y, and z.
 */
public class Function {
    private Quantity root;
    private Variable x;
    private Variable y;
    private Variable z;

    /**
     * Constructs a new Function object with the given root Quantity and variables x, y and z.
     *
     * @param root The root Quantity of the function.
     * @param x    The x variable of the function.
     * @param y    The y variable of the function.
     * @param z    The z variable of the function.
     */
    public Function(Quantity root, Variable x, Variable y, Variable z) {
        this.root = root;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Evaluates the function at the given values of x, y and z.
     *
     * @param x The value of x to evaluate the function at.
     * @param y The value of y to evaluate the function at.
     * @param z The value of z to evaluate the function at.
     * @return The result of evaluating the function.
     */
    public double evaluateAt(double x, double y, double z) {
        this.x.set(x);
        this.y.set(y);
        this.z.set(z);
        return root.getValue();
    }
}
