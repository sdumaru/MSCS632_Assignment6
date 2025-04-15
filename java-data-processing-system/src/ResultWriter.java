import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultWriter {
    private static final String OUTPUT_FILE = "../output/results.txt";

    public static synchronized void writeResult(String result) {
        // Ensure the output directory exists. Create if it does not exist
        File file = new File(OUTPUT_FILE);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        // Write result to file in append mode
        try (FileWriter fw = new FileWriter(file, true);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.println(result);
        } catch (IOException e) {
            System.err.println("Error writing result: " + e.getMessage());
        }
    }
}
