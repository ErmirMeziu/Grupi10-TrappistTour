package trappist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class PythonEvaluator {
    private static final String HOST = "localhost";
    private static final int PORT = 5555;

    public static double[] evaluate(double[] vector) {

        try (Socket socket = new Socket(HOST, PORT)) {

            OutputStream out = socket.getOutputStream();

            String vectorJson = Arrays.toString(vector);

            out.write(vectorJson.getBytes());
            out.flush();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String line = reader.readLine();    // dv,time
            if (line == null || !line.contains(",")) {
                return new double[]{9999999, 9999999};
            }

            String[] parts = line.split(",");
            double dv = Double.parseDouble(parts[0]);
            double time = Double.parseDouble(parts[1]);

            return new double[]{dv, time};

        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{9999999, 9999999};
        }
    }
}