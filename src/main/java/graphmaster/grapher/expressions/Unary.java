package graphmaster.grapher.expressions;

public abstract class Unary extends Quantity {

    protected Quantity q;

    public Unary(Quantity q) {
        this.q = q;
    }

    @Override
    public abstract double getValue();
}
