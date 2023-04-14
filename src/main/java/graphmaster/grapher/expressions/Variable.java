package graphmaster.grapher.expressions;

/**
 * This class represents a variable that can hold a numerical value.
 */
public class Variable extends Number {

    /**
     * Constructs a new Variable object with an initial value of 0.0.
     */
    public Variable() {
        super(0.0);
    }

    /**
     * Sets the value of the variable.
     *
     * @param num the value to set the variable to.
     */
    public void set(double num) {
        this.num = num;
    }
}
