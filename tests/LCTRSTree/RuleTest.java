package LCTRSTree;

import LCTRSTree.Expressions.Constant;
import LCTRSTree.Expressions.Expression;
import LCTRSTree.Expressions.Operation;
import LCTRSTree.Expressions.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {
    Rule rule;

    @BeforeEach
    void setUp() {
        ArrayList<String> params = new ArrayList<>();
        params.add("x + 1");

        ArrayList<Expression> args = new ArrayList<>();
        args.add(new Operation(new Constant("2"), "+", new Variable("y")));

        rule = new Rule("l1", params, "l3", args,
                "x < y");
    }

    @Test
    void getArguments() {
        assertEquals(Collections.singletonList("x + 1"), rule.getArguments());
    }

    @Test
    void getParameters() {
        Expression testParam = new Operation(new Constant("2"), "+", new Variable("y"));
        assertEquals(Collections.singletonList(testParam).toString(), rule.getParameters().toString());
    }

    @Test
    void getTargetRule() {
        assertEquals("l3", rule.getTargetRule());
    }

    @Test
    void getSourceRule() {
        assertEquals("l1", rule.getSourceRule());
    }

    @Test
    void getConstraint() {
        assertEquals("x < y", rule.getConstraint());
    }
}