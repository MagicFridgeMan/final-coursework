package Integration;

import LCTRSTree.CoraStringBuilder;
import LCTRSTree.Visitors.FunctionVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.LctrsLexer;
import parser.LctrsParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class CoraStringBuilderTest {

    @Test
    void testCoraStringBuilderReturn() throws IOException {
        String expected = readFile("examples/expected/return.txt");
        String actual = PythonToCoraString("examples/return.py");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCoraStringBuilderParameter() throws IOException {
        String expected = readFile("examples/expected/parameter.txt");
        String actual = PythonToCoraString("examples/parameter.py");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCoraStringBuilderMultiParameter() throws IOException {
        String expected = readFile("examples/expected/parameter.txt");
        String actual = PythonToCoraString("examples/parameter.py");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCoraStringBuilderExpression() throws IOException {
        String expected = readFile("examples/expected/expression.txt");
        String actual = PythonToCoraString("examples/expression.py");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCoraStringBuilderMultiExpression() throws IOException {
        String expected = readFile("examples/expected/multiexpression.txt");
        String actual = PythonToCoraString("examples/multiexpression.py");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCoraStringBuilderBranch() throws IOException {
        String expected = readFile("examples/expected/branch.txt");
        String actual = PythonToCoraString("examples/branch.py");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCoraStringBuilderWhile() throws IOException {
        String expected = readFile("examples/expected/while.txt");
        String actual = PythonToCoraString("examples/while.py");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCoraStringBuilderMultiWhile() throws IOException {
        String expected = readFile("examples/expected/multiwhile.txt");
        String actual = PythonToCoraString("examples/multiwhile.py");

        Assertions.assertEquals(expected, actual);
    }



    private String readFile(String fileName) throws IOException {
        return Files.readString(Path.of(fileName), StandardCharsets.UTF_8);
    }

    private String PythonToCoraString (String fileName) {
        String outString = "";
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            ANTLRInputStream input = new ANTLRInputStream(fis);
            LctrsLexer lexer = new LctrsLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LctrsParser parser = new LctrsParser(tokens);
            FunctionVisitor visitor = new FunctionVisitor();
            LctrsParser.FuncdefContext tree = parser.funcdef();
            visitor.visit(tree);
            CoraStringBuilder builder = new CoraStringBuilder(visitor.getProgram());
            outString = builder.toCoraString();
            fis.close();
        } catch (IOException e) {
            System.out.println("Could not open file " + fileName);
        }
        return outString;
    }
}