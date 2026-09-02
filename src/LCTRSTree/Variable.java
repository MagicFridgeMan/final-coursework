package LCTRSTree;

/**
 * A variable type of expression which is created when a variable is found when parsing through
 * the Python Tree created by ANTLR
 *
 * @author Andrew Harrison
 */

public class Variable implements Expression {
    public String variable;
    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString(){
        return variable;
    }
}
