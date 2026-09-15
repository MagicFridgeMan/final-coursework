import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Files.readString;

public class EndToEndTests {
    @Test
    void EndToEndTestExpression() throws IOException {
        String examplefile = "examples/expression.py";
        String outfilename = "expression";

        String[] args = new String[2];
        args[0] = examplefile;
        args[1] = outfilename;

        PythonToCora.main(args);

        String expected = Files.readString(Path.of("examples/expected/expression.txt"));
        String actual = readString(Path.of("expression.lctrs"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void EndToEndTestFunction() throws IOException {
        String examplefile = "examples/parameter.py";
        String outfilename = "parameter";

        String[] args = new String[2];
        args[0] = examplefile;
        args[1] = outfilename;

        PythonToCora.main(args);

        String expected = Files.readString(Path.of("examples/expected/parameter.txt"));
        String actual = readString(Path.of("parameter.lctrs"));

        Assertions.assertEquals(expected, actual);
    }
}
