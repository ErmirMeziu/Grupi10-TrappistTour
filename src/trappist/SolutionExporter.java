package trappist;

import java.io.FileWriter;

public class SolutionExporter {

    public static void exportToFile(double[] vec, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("[");
            for (int i = 0; i < vec.length; i++) {
                fw.write(Double.toString(vec[i]));
                if (i < vec.length - 1) fw.write(", ");
            }
            fw.write("]");
            System.out.println("Saved JSON → " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}