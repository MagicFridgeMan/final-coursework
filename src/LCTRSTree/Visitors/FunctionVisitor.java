package LCTRSTree.Visitors;

import LCTRSTree.Expressions.Expression;
import LCTRSTree.Expressions.Variable;
import LCTRSTree.Program;
import parser.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
    String constraint;
    String endOfBranchSymbol;

    public FunctionVisitor() {
        program = new Program();
        symbolCount = 0;
        currentSymbol = "";
        constraint = "";
        endOfBranchSymbol = "";
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
     * Visitor function for an Assignment context within the Python Parse Tree, it will handle the updating of the parameter
     * list and the creation of the rules for a given assignment denoted by l + some integer number
     * @param ctx the parse tree assignment context
     * @return null
     */

    @Override
    public Void visitAssignment(LctrsParser.AssignmentContext ctx) {
        ExpressionVisitor expressionVisitor = new ExpressionVisitor();

        String source = currentSymbol;
        ArrayList<String> sourceParams = new ArrayList<>(parameters);
        String assignmentVariable = ctx.IDENTIFIER().getText();

        if (!parameters.contains(assignmentVariable)) {
            parameters.add(ctx.IDENTIFIER().getText());
        }

        Expression assignmentExpression = expressionVisitor.visit(ctx.expr());
        ArrayList<Expression> expressions = createExpressionList(assignmentExpression, parameters, assignmentVariable);

        if (Objects.equals(endOfBranchSymbol, "")) {
            String TargetSymbol = "l" + ++symbolCount;
            program.addDeclaration(true, TargetSymbol, parameters.size());
            program.addRule(source, sourceParams, TargetSymbol, expressions, constraint);
            currentSymbol = TargetSymbol;
        } else {
            program.addRule(source, sourceParams, endOfBranchSymbol, expressions, constraint);
        }

        endOfBranchSymbol = "";
        constraint = "";


        return null;
    }

    /**
     * Visitor for an if statement context handling the branch outputs by looping through both the bodies in the
     * true and false branches within if statment
     * @param ctx the parse tree if statement context
     * @return null
     */

    @Override
    public Void visitIf_stmt(LctrsParser.If_stmtContext ctx) {
        String sourceBranchSymbol = "l" + ++symbolCount;
        ArrayList<String> parametersAtBranch = new ArrayList<>(parameters);


        program.addDeclaration(true, sourceBranchSymbol, parameters.size());
        program.addRule(currentSymbol, parameters, sourceBranchSymbol, createExpressionList(parameters), "");

        String[] constraintStrings = getConstraintStrings(ctx.constraint());
        String enterBranchConstraint = constraintStrings[0];
        String skipBranchConstraint = constraintStrings[1];

        String branchExitSymbol = "l" + ++symbolCount;

        parameters = new ArrayList<>(parametersAtBranch);
        currentSymbol = sourceBranchSymbol;
        constraint = enterBranchConstraint;

        makeIfBranch(ctx.body(0), branchExitSymbol);

        program.addDeclaration(true, branchExitSymbol, parameters.size());   // add this line here

        parameters = new ArrayList<>(parametersAtBranch);
        currentSymbol = sourceBranchSymbol;
        constraint = skipBranchConstraint;

        makeIfBranch(ctx.body(1), branchExitSymbol);

        currentSymbol = branchExitSymbol;

        return null;
    }

    @Override
    public Void visitWhile_stmt(LctrsParser.While_stmtContext ctx) {
        String entrySymbol = currentSymbol;
        ArrayList<String> loopEntryParams = new ArrayList<>(parameters);

        String loopSymbol = "l" + ++symbolCount;
        program.addDeclaration(true, loopSymbol, parameters.size());
        program.addRule(currentSymbol, parameters, loopSymbol, createExpressionList(parameters), "");

        String[] constraintStrings = getConstraintStrings(ctx.constraint());
        String enterBranchConstraint = constraintStrings[0];
        String skipBranchConstraint = constraintStrings[1];

        String branchExitSymbol = "l" + ++symbolCount;
        program.addDeclaration(true, branchExitSymbol, parameters.size());
        program.addRule(loopSymbol, loopEntryParams,  branchExitSymbol, createExpressionList(loopEntryParams), skipBranchConstraint);

        parameters = new ArrayList<>(loopEntryParams);
        currentSymbol = loopSymbol;
        constraint = enterBranchConstraint;
        makeIfBranch(ctx.body(), loopSymbol);

        parameters = new ArrayList<>(loopEntryParams);
        currentSymbol = branchExitSymbol;

        return null;
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
        program.addDeclaration(true, returnName, returnExpression.size());
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

    /**
     * Helper function to replace the given argument in a target rule with the assignment it is given.
     * @param expression Expression to replace the given parameter passed in
     * @param params list of all parameters currently found within the program
     * @param paramToReplace parameter to be replaced with the expression string
     * @return A list of expressions to be used in the source rules
     */
    private ArrayList<Expression> createExpressionList(Expression expression, List<String> params, String paramToReplace) {
        ArrayList<Expression> ExpressionList = new ArrayList<>();
        for (String param : params) {
            if (param.equals(paramToReplace)) {
                ExpressionList.add(expression);
            } else {
                ExpressionList.add(new Variable(param));
            }
        }
        return ExpressionList;
    }

    /**
     * Expression list method for creating a standard expression list from a given list of string params.
     * @param params are the parameters to be conversting into an expression list
     * @return the list of new variables as expressions
     */

    private ArrayList<Expression> createExpressionList(List<String> params) {
        ArrayList<Expression> assignmentExpressionList = new ArrayList<>();
        for (String param : params) {
            assignmentExpressionList.add(new Variable(param));
        }

        return assignmentExpressionList;
    }

    /**
     * Helper method to get the inverse symbol for a branch statement within the code
     * @param constraint is the constraint to be inverted
     * @return the inverted symbol
     */

    private String invertConstraint(String constraint) {
        return switch (constraint) {
            case "==" -> "!=";
            case "!=" -> "==";
            case "<" -> ">=";
            case "<=" -> ">";
            case ">" -> "<=";
            case ">=" -> "<";
            default -> throw new IllegalArgumentException("Invalid constraint " + constraint);
        };
    }

    /**
     * Helper function to create a branch for the if branch statement, it visits through each statement inside the body
     * and will create an exit rule if there are no more statements within the Iterator
     * @param ctx body context to iterate over
     * @param exitBranchSymbol the symbol of the exit target rule
     * @return null
     */

    private Void makeIfBranch(LctrsParser.BodyContext ctx , String exitBranchSymbol) {

        Iterator<LctrsParser.StmtContext> iterator = ctx.stmt().iterator();
        while(iterator.hasNext()){
            LctrsParser.StmtContext stmt = iterator.next();
            if (iterator.hasNext()){
                visit(stmt);
            } else {
                endOfBranchSymbol = exitBranchSymbol;
                visit(stmt);
            }
        }
        return null;
    }

    private String[] getConstraintStrings(LctrsParser.ConstraintContext ctx) {
        ExpressionVisitor expressionVisitor = new ExpressionVisitor();
        String comparisonSymbol = ctx.comparator().getText();
        String inverseComparisonSymbol = invertConstraint(comparisonSymbol);
        String left = expressionVisitor.visit(ctx.expr(0)).toString();
        String right = expressionVisitor.visit(ctx.expr(1)).toString();
        String enterBranchString = left + " " + comparisonSymbol + " " + right;
        String skipBranchString = left + " " + inverseComparisonSymbol + " " + right;
        String[] constraintStrings = new String[2];
        constraintStrings[0] = enterBranchString;
        constraintStrings[1] = skipBranchString;

        return constraintStrings;
    }

    public Program getProgram() {
        return program;
    }
}
