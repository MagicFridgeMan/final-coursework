package LCTRSTree;

import LCTRSTree.Expressions.Expression;

import java.util.ArrayList;

/**
 * A class representing the Cora program that contains the list of both the declarations and the rules that the
 * program is following and will output.
 */

public class Program {
    String name;
    ArrayList<Declaration> declarations;
    ArrayList<Rule> rules;

    public Program() {
        this.declarations = new ArrayList<>();
        this.rules = new ArrayList<>();
    }

    public void addDeclaration(boolean isPrivate, String name, Integer sorts) {
        this.declarations.add(new Declaration(isPrivate, name, sorts));
    }

    public void addRule(String sourceRule, ArrayList<String> arguments, String targetRule, ArrayList<Expression> parameters, String constraint) {
        this.rules.add(new Rule(sourceRule, arguments, targetRule, parameters, constraint));
    }

    public ArrayList<Declaration> getDeclarations() {
        return declarations;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
