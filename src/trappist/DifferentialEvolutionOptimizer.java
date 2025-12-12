package trappist;

public class DifferentialEvolutionOptimizer {

    private final int populationSize = 100;
    private final int maxIter = 1000;
    private final double F = 0.5;
    private final double CR = 0.9;

    private final TrajectoryEvaluator evaluator;

    public DifferentialEvolutionOptimizer(TrajectoryEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public double[] optimize(double[] initial) {

        int dim = initial.length;

        double[][] pop = new double[populationSize][dim];
        double[] score = new double[populationSize];

        //STAGNATION TRACKING
        double lastBestDV = Double.MAX_VALUE;
        int stagnationCount = 0;

        // INITIAL POPULATION
        for (int i = 0; i < populationSize; i++) {

            pop[i] = initial.clone();

            for (int j = 0; j < dim; j++) {
                pop[i][j] += (Math.random() - 0.5) * 0.5;
            }

            double[] res = evaluator.evaluate(pop[i]);
            score[i] = res[0];
        }