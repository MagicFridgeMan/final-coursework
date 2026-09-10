package LCTRSTree;

import LCTRSTree.Expressions.Expression;

import java.util.ArrayList;

/**
 * A class representing a Cora rule, storing information about both the left and right hand side of a rule as well
 * as the optional guard.
 */

public class Rule {
    private final String sourceRule;
    private final ArrayList<String> arguments;
    private final String targetRule;
    private final ArrayList<Expression> parameters;
    private final String constraint;

    public Rule(String sourceRule, ArrayList<String> arguments, String targetRule, ArrayList<Expression> parameters, String constraint) {
        this.sourceRule = sourceRule;
        this.arguments = arguments;
        this.targetRule = targetRule;
        this.parameters = parameters;
        this.constraint = constraint;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }

    public ArrayList<Expression> getParameters() {
        return parameters;
    }

    public String getTargetRule() {
        return targetRule;
    }

    public String getSourceRule() {
        return sourceRule;
    }

    public String getConstraint() {
        return constraint;
    }
}
