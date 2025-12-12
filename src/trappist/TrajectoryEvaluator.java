package trappist;

public class TrajectoryEvaluator {

    public static double[] evaluate(double[] vector) {
        return PythonEvaluator.evaluate(vector);
    }
}