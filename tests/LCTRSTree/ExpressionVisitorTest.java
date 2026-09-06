package LCTRSTree;

import LCTRSTree.Expressions.Expression;
import LCTRSTree.Visitors.ExpressionVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.LctrsLexer;
import parser.LctrsParser;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionVisitorTest {
    ExpressionVisitor visitor;

    @BeforeEach
    void setup() {
        visitor = new ExpressionVisitor();
    }

    public Expression parseExpr(String input) {
        CharStream stream = CharStreams.fromString(input);
        LctrsLexer lexer = new LctrsLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LctrsParser parser = new LctrsParser(tokens);
        return visitor.visit(parser.expr());
    }

    public Expression parseConstraint(String input) {
        CharStream stream = CharStreams.fromString(input);
        LctrsLexer lexer = new LctrsLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LctrsParser parser = new LctrsParser(tokens);
        return visitor.visit(parser.constraint());
    }

    @Test
    void bareVariable() {
        assertEquals("x", parseExpr("x").toString());
    }

    @Test
    void bareConstant() {
        assertEquals("2", parseExpr("2").toString());
    }

    @Test
    void standardExpression() {
        assertEquals("(x + 1)", parseExpr("x + 1").toString());
    }

    @Test
    void precedence() {
        assertEquals("(x + (y * 2))", parseExpr("x + y * 2").toString());
    }

    @Test
    void comparison() {
        assertEquals("x < y", parseConstraint("x < y").toString());
    }

    @Test
    void comparisonAndExpressionTest() {
        assertEquals("x < (y + 1)", parseConstraint("x < y + 1").toString());
    }
}