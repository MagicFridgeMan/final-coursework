package LCTRSTree.Expressions;

/**
 * Interface class used to identify an expression, any new expression files should implement this empty interface to be
 * allowed to be used as an expression. Any expression must implement their own versions of the to String method
 */

public interface Expression {
    @Override
    public String toString();
}
