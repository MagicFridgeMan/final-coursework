package LCTRSTree;

/**
 * A constant object of an expression which is created when a constant is found when parsing through
 * the Python Tree created by ANTLR
 *
 * @author Andrew Harrison
 */

public class Constant implements Expression {
    public String constant;

    public Constant(String constant) {
        this.constant = constant;
    }

    @Override
    public String toString(){
        return  constant;
    }
}
