package LCTRSTree.Visitors;

import LCTRSTree.Expressions.Expression;
import LCTRSTree.Program;
import parser.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A visitor method extending the LctrsParserBaseVisitor and working with Void values. These visitor functions do not
 * create anything new and instead modify a program which is created upon instantiation of a new Function visitor.
 * Methods in this file add Declarations to Rules to the program to be used later by a file or string writer to output
 * and LCTRS program
 * It creates a new program to store the relevant information needed for the file writer to be able to create a
 * relevant output file.
 */


public class FunctionVisitor extends LctrsParserBaseVisitor<Void> {
    ArrayList<String> parameters;
    Program program;
    int symbolCount;
    String currentSymbol;

    public FunctionVisitor() {
        program = new Program();
        symbolCount = 0;
        currentSymbol = "";
    }


    /**
     * Visitor for the funcdef context inside the Python Parse Tree. It grabs the relevant fields needed to update
     * the program and creates the first declaration for the function declaration inside the program.
     * Program returns null because w
     * @param ctx the parse tree funcdef context to be traversed through
     * @return null
     */
    @Override
    public Void visitFuncdef(LctrsParser.FuncdefContext ctx) {
        String funcName = ctx.IDENTIFIER().getText();
        currentSymbol = funcName;
        program.setName(funcName);
        parameters = (ctx.parameters() == null) ? new ArrayList<>() : getInitParameters(ctx.parameters());
        program.addDeclaration(false, currentSymbol, parameters.size());
        return visitChildren(ctx);
    }

    /**
     * Visitor function for a return stmt, generating a new declaration and rule to be used upon visiting a return_stmt
     * context. Updates the program with the relevant declaration and rule to be output later.
     *
     * @param ctx the parse tree return_stmt context to be traversed
     * @return null
     */
    @Override
    public Void visitReturn_stmt(LctrsParser.Return_stmtContext ctx) {
        ExpressionVisitor expressionVisitor = new ExpressionVisitor();
        String returnName = "return_" + program.getName();
        ArrayList<Expression> returnExpression = new ArrayList<>(List.of(expressionVisitor.visit(ctx.expr())));
        program.addDeclaration(false, returnName, returnExpression.size());
        program.addRule(currentSymbol, new ArrayList<>(parameters), returnName, returnExpression, "");
        return null;
    }

    /**
     * Private helper function to get the initial parameters used in the program fromthe funcdef context
     * @param ctx the ParameterContext to be traversed
     * @return an ArrayList of strings of the parameter names.
     */

    private ArrayList<String> getInitParameters(LctrsParser.ParametersContext ctx) {
        ArrayList<String> parameters = new ArrayList<>();
        for (LctrsParser.ParameterContext p : ctx.parameter()) {
            parameters.add(p.IDENTIFIER().getText());
        }
        return parameters;
    }

    public Program getProgram() {
        return program;
    }
}
