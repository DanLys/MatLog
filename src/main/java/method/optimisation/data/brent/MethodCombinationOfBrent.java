package method.optimisation.data.brent;

public class MethodCombinationOfBrent {
    private final static double EPSILON = 1E-9;

    private static double f(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
    }

    // Return x : f(x) = min
    private static double combinationOfBrent(double l, double r, int iterations) {
        // Step 1
        final double t = (3 - Math.sqrt(5)) / 2;
        double a = l, c = r;
        double x = a + t * (c - a), w = x, v = x;
        double fX = f(x), fW = fX, fV = fX;
        double d = c - a, e = d;
        double u = 0, fU;
        boolean parabolaU;
        // Step 2
        while (d > EPSILON) {
            // Step 3
            parabolaU = false;
            double g = e;
            e = d;
            double tol = EPSILON * Math.abs(x) + EPSILON / 10;
            if (Math.abs(x - (a + c) / 2) + (c - a) / 2 - 2 * tol <= EPSILON) {
                break;
            }
            if (!(x == w || x == v || w == v || fX == fW || fX == fV || fV == fW)) {
                u = (x + w - (fW - fX) / (w - x) / ((fV - fX) / (v - x) - (fW - fX) / (w - x)) / (v - w)) / 2;
                if (u - a >= 0 && c - u >= 0 && Math.abs(u - x) - g / 2 < 0) {
                    parabolaU = true;
                    if (u - a - 2 * tol < EPSILON || c - u - 2 * tol < EPSILON) {
                        u = x - Math.signum(x - (a + c) / 2) * tol;
                    }
                } else {
                    parabolaU = false;
                }
            }
            // Step 4
            if (!parabolaU) {
                if (x - (a + c) / 2 < EPSILON) {
                    u = x + t * (c - x);
                    e = c - x;
                } else {
                    u = x - t * (x - a);
                    e = x - a;
                }
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
                    c = x;
                }
                v = w;
                w = x;
                x = u;
                fV = fW;
                fW = fX;
                fX = fU;
            } else {
                if (u - x >= EPSILON) {
                    c = u;
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
        }
        // Step 6
        return x;
    }

    public static void main(String[] args) {
        System.out.print(f(combinationOfBrent(0.5, 2.5, 100)));
    }
}
