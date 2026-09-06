package LCTRSTree.Expressions;

/**
 * A class for a constraint which stores the information of an operation from the Python Tree. An operation is defined
 * as a compound expression where we have 2 expressions seperated by some comparator symbol.
 */

public class Constraint implements Expression{
    String op;
    Expression left;
    Expression right;

    public Constraint(Expression left, String op, Expression right) {
            this.op = op;
            this.left = left;
            this.right = right;
        }

    @Override
    public String toString() {
        return left.toString() + " " + op + " " + right.toString();
    }
}
