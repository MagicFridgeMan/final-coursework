import java.io.FileWriter;
import java.io.IOException;

public class CoraFileWriter {
    public static void Write(String coraString, String filename){
        try {
            FileWriter fw = new FileWriter(filename + ".lctrs");
            fw.write(coraString);
            fw.close();
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
