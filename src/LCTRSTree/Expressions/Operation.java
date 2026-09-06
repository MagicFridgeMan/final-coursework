package LCTRSTree.Expressions;

/**
 * A class for an operation which stores the information of an operation from the Python Tree. An operation is defined
 * as a compound expression where we have 2 expressions seperated by some arithmetic symbol.
 */

public class Operation implements Expression {
    String op;
    Expression left;
    Expression right;

    public Operation(Expression left, String op, Expression right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
