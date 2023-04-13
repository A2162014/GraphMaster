package graphmaster.grapher.expressions;

public class Sine extends Unary {

    public Sine(Quantity q) {
        super(q);
    }

    @Override
    public double getValue() {
        double val = realValue(q);
        return Math.sin(val);
    }
}
