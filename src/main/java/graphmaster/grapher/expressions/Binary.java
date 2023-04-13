package graphmaster.grapher.expressions;

abstract class Binary extends Quantity {

    Quantity q1;
    Quantity q2;

    Binary(Quantity q1, Quantity q2) {
        this.q1 = q1;
        this.q2 = q2;
    }

}
