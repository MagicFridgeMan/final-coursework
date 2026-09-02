package LCTRSTree;

/**
 * A class which stores the information of a compound operation from the Python Tree. An operation is defined
 * as a compound expression where we have 2 expressions seperation by some arithmetic operation such as +, -, // etc
 */

public class Operation implements Expression {
    String operation;
    Expression left;
    Expression right;

    public Operation(Expression left, String operation, Expression right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" +  left.toString() + " " + operation + " " + right.toString() + ")";
    }
}
