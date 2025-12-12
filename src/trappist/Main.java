package trappist;

public class Main {

    public static void main(String[] args) {

        System.out.println("Generating initial feasible vector…");
        double[] start = FeasibleInitializer.generate();

        TrajectoryEvaluator evaluator = new TrajectoryEvaluator();
        DifferentialEvolutionOptimizer de = new DifferentialEvolutionOptimizer(evaluator);

        double[] best = de.optimize(start);

        double[] result = evaluator.evaluate(best);
        double dv = result[0];
        double time = result[1];

        System.out.println("FINAL DV   = " + dv);
        System.out.println("FINAL TIME = " + time);

        SolutionExporter.exportToFile(best, "solution_vector.json");

        System.out.println("DONE.");
    }
}