package LCTRSTree;

public class Operation implements Expression {
    String operation;
    Expression left;
    Expression right;

    public Operation(Expression left, String name, Expression right) {
        this.operation = name;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" +  left.toString() + " " + operation + " " + right.toString() + ")";
    }
}
