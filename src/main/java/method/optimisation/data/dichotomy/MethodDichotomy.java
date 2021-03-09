package method.optimisation.data.dichotomy;

import method.optimisation.data.AbstractComparator;
import org.springframework.stereotype.Component;

/**
 * Created by Danil Lyskin at 15:12 25.02.2021
 */

@Component
public class MethodDichotomy extends AbstractComparator {

    private double func(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow(x - 2.3, 2);
    }

    /**
     * calculate the result for 0.2𝑥𝑙𝑔(𝑥) + (𝑥 − 2.3)^2
     * @return 1 if result > 0, 0 if |result - 0| < eps, -1 if result < 0 {@link Integer}
     */
    private Integer check(double x1, double x2) {
        double res1 = func(x1);
        double res2 = func(x2);

        if (isBigger(res2, res1)) {
            return 1;
        } else if (isEqual(res1, res2)) {
            return 0;
        }
        return -1;
    }

    /**
     * method dichotomy
     * @return x which is answer {@link Double}
     */
    public double solve(double a, double b, int iterations) {
        double le = a;
        double ri = b;
        double del = 1e-4;

        while (iterations > 0) {
            double x1 = (le + ri) / 2 - del;
            double x2 = (le + ri) / 2 + del;

            if (check(x1, x2) > 0) {
                ri = x2;
            } else {
                le = x1;
            }
            iterations--;
        }

        return (le + ri) / 2;
    }
}
