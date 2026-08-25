import java.util.ArrayList;
import java.util.List;

public class LctrsTreeGenerator extends LctrsParserBaseVisitor<Void> {
    String funcName = "";
    //SymbolTable symbolTable = new SymbolTable();


    @Override
    public Void visitFuncdef(LctrsParser.FuncdefContext ctx) {
        funcName = ctx.IDENTIFIER().getText();
        System.out.println(funcName);
        return super.visitChildren(ctx);
    }

    @Override
    public Void visitReturn_stmt(LctrsParser.Return_stmtContext ctx) {
        return super.visitReturn_stmt(ctx);
    }

    @Override
    public Void visitParamlist(LctrsParser.ParamlistContext ctx) {
        System.out.println(ctx.getText());
        List<String> paramlist = new ArrayList<String>();
    }
}

//    @Override
//    public String visitParameter{}
//}
