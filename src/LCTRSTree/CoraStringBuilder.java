package LCTRSTree;

import LCTRSTree.Expressions.Expression;

import java.util.ArrayList;

/**
 * Builder class for the expected Cora output string. It expects a program and will print the program out in order
 */

public class CoraStringBuilder {
    private final Program program;

    public CoraStringBuilder(Program program) {
        this.program = program;
    }

    public String toCoraString() {
        StringBuilder builder = new StringBuilder();
            for (Declaration d : program.getDeclarations()) {
                String privateString = d.isPrivate() ? "private " : "";
                builder.append(privateString);
                builder.append(d.getName() + " :: ");
                builder.append("Int -> ".repeat(d.getSorts()));
                builder.append("A");
                builder.append("\n");
            }
            builder.append("\n");

            for (Rule r : program.getRules()) {
                builder.append(r.getSourceRule());
                builder.append("(" + argsToString(r.getArguments()) + ")");
                builder.append(" -> ");
                builder.append(r.getTargetRule());
                builder.append("(" + paramsToString(r.getParameters()) + ")");
                String constraint = (r.getConstraint().isEmpty()) ? "" : " | " + r.getConstraint();
                builder.append(constraint);
            }
            return builder.toString();
    }

    private String argsToString(ArrayList<String> stringList){
       StringBuilder builder = new StringBuilder();
       boolean first = true;
        for (String s: stringList){
            if (first) {
                first = false;
                builder.append(s);
            } else {
                builder.append(", " +  s);
            }
        }
        return builder.toString();
    }

    private String paramsToString(ArrayList<Expression> expressionList){
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Expression e: expressionList){
            if (first) {
                first = false;
                builder.append(e.toString());
            } else {
                builder.append(", " +  e.toString());
            }
        }
        return builder.toString();
    }
}
