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

        // MAIN ITERATION LOOP
        for (int iter = 0; iter < maxIter; iter++) {

            for (int i = 0; i < populationSize; i++) {

                int a, b, c;
                do { a = rand(populationSize); } while (a == i);
                do { b = rand(populationSize); } while (b == i || b == a);
                do { c = rand(populationSize); } while (c == i || c == a || c == b);

                double[] trial = new double[dim];

                for (int j = 0; j < dim; j++) {
                    if (Math.random() < CR)
                        trial[j] = pop[a][j] + F * (pop[b][j] - pop[c][j]);
                    else
                        trial[j] = pop[i][j];
                }

                clampVector(trial);

                double[] trialRes = evaluator.evaluate(trial);
                double trialDV = trialRes[0] + 1e6 * (trialRes.length > 2 ? trialRes[2] + trialRes[3] : 0);

                if (trialDV < score[i]) {
                    pop[i] = trial;
                    score[i] = trialDV;
                }
            }

            // GET BEST DV
            double bestDV = score[0];
            for (int k = 1; k < populationSize; k++) {
                if (score[k] < bestDV)
                    bestDV = score[k];
            }

            System.out.println("ITERATION " + iter + " → BEST DV = " + bestDV);

            // STAGNATION CHECK
            if (Math.abs(bestDV - lastBestDV) < 1e-9) {
                stagnationCount++;
            } else {
                stagnationCount = 0;
                lastBestDV = bestDV;
            }

            // RESTART IF STAGNANT 20 ITERATIONS
            if (stagnationCount >= 20) {
                System.out.println(">>> STAGNATION DETECTED — RESTARTING 30% OF POPULATION <<<");

                int resetCount = (int)(populationSize * 0.3);

                for (int r = 0; r < resetCount; r++) {
                    int idx = rand(populationSize);

                    for (int j = 0; j < dim; j++) {
                        pop[idx][j] = initial[j] + (Math.random() - 0.5) * 2.0;
                    }

                    clampVector(pop[idx]);
                    double[] res = evaluator.evaluate(pop[idx]);
                    score[idx] = res[0];
                }

                stagnationCount = 0;
            }
        }