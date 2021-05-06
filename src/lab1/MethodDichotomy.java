package lab1;

import java.util.function.Function;

/**
 * Created by Danil Lyskin at 15:12 25.02.2021
 */

public class MethodDichotomy extends Minimalizer {


    public MethodDichotomy(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    private static double func(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow(x - 2.3, 2);
    }


    @Override
    public double getMinimalValue(double epsilon) {
        return solve(epsilon);
    }

    /**
     * calculate the result for 0.2𝑥𝑙𝑔(𝑥) + (𝑥 − 2.3)^2
     * @return 1 if result > 0, 0 if |result - 0| < eps, -1 if result < 0 {@link Integer}
     */
    private static Integer check(double x1, double x2) {
        double res1 = func(x1);
        double res2 = func(x2);

        if (res2 > res1) {
            return 1;
        }
        return -1;
    }

    /**
     * method dichotomy
     * @return x which is answer {@link Double}
     */
    public  Pair<Double, Double> solve(final double EPS) {
        double le = leftBorder;
        double ri = rightBorder;
        double del = 1e-7;

        while (ri - le > EPS) {
            double x1 = (le + ri - del) / 2;
            double x2 = (le + ri + del) / 2;

            if (check(x1, x2) == 1) {
                ri = x2;
            } else {
                le = x1;
            }
        }

        return new lab1.Pair<Double, Double>(le, ri);
    }


}
