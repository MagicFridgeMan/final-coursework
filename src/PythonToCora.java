import LCTRSTree.CoraStringBuilder;
import LCTRSTree.Visitors.FunctionVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.LctrsLexer;
import parser.LctrsParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PythonToCora {

    static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }

        if (!args[0].endsWith(".py")) {
            throw new IllegalArgumentException("Input file must be a python file");
        }

        String fileName = args[0];
        String outname = args[1];

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
            CoraFileWriter.Write(builder.toCoraString(), outname);
            IO.println("Cora program written to " + outname + ".txt");
            fis.close();
        } catch (IOException e) {
            IO.println("Could not open file " + fileName);
        }
    }
}

