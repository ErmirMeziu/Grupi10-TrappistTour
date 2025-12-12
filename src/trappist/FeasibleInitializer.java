package trappist;

import java.util.Random;

public class FeasibleInitializer {

    public static final int PLANETS = 7;

    private static final Random rand = new Random(123);

    // Very low-bending flyby params (favor low DV)
    private static final double FIXED_BETA = 0.5;    // gentle 30° turn
    private static final double FIXED_RP   = 3.0;    // safer, less bending
    private static final double FIXED_ETA  = 0.5;    // symmetric DSM

    // TRAPPIST orbital periods for synodic TOF guess
    private static final double[] ORBITAL_PERIODS = {
            1.51, 2.42, 4.04, 6.09, 9.20, 12.35, 18.76
    };

    private static double synodic(int i, int j) {
        double Pi = ORBITAL_PERIODS[i];
        double Pj = ORBITAL_PERIODS[j];
        double inv = Math.abs(1.0 / Pi - 1.0 / Pj);
        if (inv == 0) return 120;
        return 1.0 / inv;
    }

    public static double[] generate() {

        int legs = PLANETS;

        int vectorLength = 3 + (legs - 1) * 4 + legs;
        double[] x = new double[vectorLength];
        int k = 0;

        // 1) INITIAL LEG
        double T0 = 4500.0;
        x[k++] = 0.5;
        x[k++] = 0.5;
        x[k++] = T0;

        // 2) PLANETARY LEGS
        for (int i = 0; i < legs - 1; i++) {

            double T_syn = synodic(i, i + 1);

            T_syn = Math.max(150, Math.min(250, T_syn));

            // VERY SMALL NOISE
            double noiseScale = 0.05; // 5%
            double noise = T_syn * noiseScale * rand.nextGaussian();
            T_syn += noise;

            if (T_syn < 150) T_syn = 150;
            if (T_syn > 300) T_syn = 300;

            // fill vector
            x[k++] = FIXED_BETA;   // beta small → low DV
            x[k++] = FIXED_RP;   // rp/rP moderate
            x[k++] = FIXED_ETA;   // eta mid-course
            x[k++] = T_syn; // safe TOF
        }

        // 3) FIXED PERMUTATION
        for (int i = 0; i < legs; i++)
            x[k++] = i;

        return x;
    }

}
