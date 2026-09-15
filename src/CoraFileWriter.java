import java.io.FileWriter;
import java.io.IOException;

public class CoraFileWriter {
    public static void Write(String coraString, String filename){
        String outfilename = filename + ".lctrs";
        try {
            FileWriter fw = new FileWriter(outfilename);
            fw.write(coraString);
            fw.close();
        } catch (IOException e){
            System.out.println("An error occurred writing to file " + outfilename);
        }
    }
}
