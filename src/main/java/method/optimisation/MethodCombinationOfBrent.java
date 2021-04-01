package method.optimisation;

public class MethodCombinationOfBrent {
    private final static double EPSILON = 1E-9;

    private static double f(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
    }

    // Return x : f(x) = min
    public static Pair<Double, Pair<Double, Double>> combinationOfBrent(double a, double b, int iterations) {
        // Step 1
        final double t = (3 - Math.sqrt(5)) / 2;
        double x = a + t * (b - a), w = x, v = x;
        double fX = f(x), fW = fX, fV = fX;
        double d = b - a, e = d;
        double u = 0, fU;
        boolean parabolaU = false;
        Pair<Double, Pair<Double, Double>> ans = new Pair<>(null, new Pair<>(a, b));
        // Step 2
        while (iterations > 0) {
            // Step 3
            parabolaU = false;
            double g = e;
            e = d;
            double tol = EPSILON * Math.abs(x) + EPSILON / 10;
            if (Math.abs(x - (a + b) / 2) + (b - a) / 2 - 2 * tol <= EPSILON) {
                break;
            }
            if (!(x == w || x == v || w == v || fX == fW || fX == fV || fV == fW)) {
                u = (x + w - (fW - fX) / (w - x) / ((fV - fX) / (v - x) - (fW - fX) / (w - x)) / (v - w)) / 2;
                if (u - a >= 0 && b - u >= 0 && Math.abs(u - x) - g / 2 < 0) {
                    parabolaU = true;
                    ans = new Pair<>(x, new Pair<>(w, v));
                    if (u - a - 2 * tol < EPSILON || b - u - 2 * tol < EPSILON) {
                        u = x - Math.signum(x - (a + b) / 2) * tol;
                    }
                } else {
                    parabolaU = false;
                }
            }
            // Step 4 // Golden ratio
            if (!parabolaU) {
                if (x - (a + b) / 2 < EPSILON) {
                    u = x + t * (b - x);
                    e = b - x;
                } else {
                    u = x - t * (x - a);
                    e = x - a;
                }
                ans = new Pair<>(null, new Pair<>(u, e));
            }
            if (Math.abs(u - x) - tol < EPSILON) {
                u = x + Math.signum(u - x) * tol;
            }
            d = Math.abs(u - x);
            fU = f(u);
            // Step 5
            if (fU - fX <= EPSILON) {
                if (u - x >= EPSILON) {
                    a = x;
                } else {
                    b = x;
                }
                v = w;
                w = x;
                x = u;
                fV = fW;
                fW = fX;
                fX = fU;
            } else {
                if (u - x >= EPSILON) {
                    b = u;
                } else {
                    a = u;
                }
                if (fU - fW <= EPSILON || w == x) {
                    v = w;
                    w = u;
                    fV = fW;
                    fW = fU;
                } else if (fU - fV <= EPSILON || v == x || v == w) {
                    v = u;
                    fV = fU;
                }
            }
            iterations--;
        }
        // Step 6
        return ans;
    }
}
