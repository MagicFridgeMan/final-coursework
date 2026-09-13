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
                builder.append(privateString)
                        .append(d.getName())
                        .append(" :: ")
                        .repeat("Int -> ", d.getSorts())
                        .append("A")
                        .append("\n");
            }

            for (Rule r : program.getRules()) {
                String constraint = (r.getConstraint().isEmpty()) ? "" : " | " + r.getConstraint();

                builder.append("\n")
                        .append(r.getSourceRule())
                        .append("(" + argsToString(r.getArguments()) + ")")
                        .append(" -> ")
                        .append(r.getTargetRule())
                        .append("(" + paramsToString(r.getParameters()) + ")")
                        .append(constraint);
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
