package method.optimisation.data.dichotomy;

import org.springframework.stereotype.Component;

/**
 * Created by Danil Lyskin at 15:12 25.02.2021
 */

@Component
public class MethodDichotomy {

    private final double EPS = 1e-7;

    /**
     * calculate the result for 0.2𝑥𝑙𝑔(𝑥) + (𝑥 − 2.3)2
     * @return 1 if result > 0, 0 if result - 0 < eps, -1 if result < 0 {@link Integer}
     */
    private Integer check(double x) {
        double result = 0.2 * x * Math.log(x) + Math.pow(x - 2.3, 2);

        if (result < -EPS) {
            return -1;
        } else if (result < EPS) {
            return 0;
        }
        return 1;
    }

    /**
     * method dichotomy
     * @return x which is answer {@link Double}
     */
    public double solve() {
        double le = 0.5 - 10 * EPS;
        double ri = 2.5 + 10 * EPS;

        while (ri - le > EPS) {
            double x = (le + ri) / 2;

            if (check(x) > 0) {
                ri = x;
            } else {
                le = x;
            }
        }

        return le;
    }
}
