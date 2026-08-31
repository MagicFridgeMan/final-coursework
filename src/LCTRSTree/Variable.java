package LCTRSTree;

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
