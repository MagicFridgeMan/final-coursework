package LCTRSTree;

public class Constant implements Expression {
    public String constant;

    public Constant(String constant) {
        this.constant = constant;
    }

//    public void accept(ExpressionVisitor visitor){
//        visitor.visit(this);
//    }

    @Override
    public String toString(){
        return  constant;
    }
}
