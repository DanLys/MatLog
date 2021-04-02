public class MethodCombinationOfBrent {
    private final static double EPSILON = 1E-9;

    private static double f(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
    }

    private static double findMinParabolaNoOrder(double x1, double x2, double x3, double f1, double f2, double f3) {
        if (x1 < x2 && x2 < x3) {
            return findMinOfParabola(x1, x2, x3, f1, f2, f3);
        }
        if (x3 < x2 && x2 < x1) {
            return findMinOfParabola(x3, x2, x1, f3, f2, f1);
        }
        if (x2 < x1 && x1 < x3) {
            return findMinOfParabola(x2, x1, x3, f2, f1, f3);
        }
        if (x3 < x1 && x1 < x2) {
            return findMinOfParabola(x3, x1, x2, f3, f1, f2);
        }
        if (x1 < x3 && x3 < x2) {
            return findMinOfParabola(x1, x3, x2, f1, f3, f2);
        }
        if (x2 < x3 && x3 < x1) {
            return findMinOfParabola(x2, x3, x1, f2, f3, f1);
        }
        return -1;
    }

    private static double findMinOfParabola(double x1, double x2, double x3, double f1, double f2, double f3) {
        double a1 = (f2 - f1) / (x2 - x1);
        double a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
        return (x1 + x2 - a1 / a2) / 2;
    }

    // Return x : f(x) = min
    public static Pair<Double, Pair<Double, Double>> combinationOfBrent(double a, double b, int iterations) {
        // Step 1
        final double t = (3 - Math.sqrt(5)) / 2;
        double x = a + t * (b - a), w = x, v = x;
        double fX = f(x), fW = fX, fV = fX;
        double d = b - a, e = d;
        double u = 0, fU;
        boolean parabolaU;
        Pair<Double, Pair<Double, Double>> ans = new Pair<>(null, new Pair<>(a, b));
        // Step 2
        while (iterations > 0) {
            // Step 3
            parabolaU = false;
            double g = e;
            e = d;
            double tol = EPSILON * Math.abs(x) + EPSILON / 10;
//            if (Math.abs(x - (a + b) / 2) + (b - a) / 2 - 2 * tol <= EPSILON) {
//                break;
//            }
            if (!(x == w || x == v || w == v || fX == fW || fX == fV || fV == fW)) {
                u = findMinParabolaNoOrder(v, x, w, fV, fX, fW);
                if (u - a >= EPSILON && b - u >= EPSILON && Math.abs(u - x) - g / 2 < EPSILON) {
                    parabolaU = true;
                    double m1 = Double.min(x, Double.min(w, v));
                    double m2 = Double.max(x, Double.max(w, v));
                    ans = new Pair<>(m1, new Pair<>((x + w + v) - (m1 + m2), m2));
                    if (u - a - 2 * tol < EPSILON || b - u - 2 * tol < EPSILON) {
                        u = x - Math.signum(x - (a + b) / 2) * tol;
                    }
                }
            }
            // Step 4
            if (!parabolaU) {
                if (x - (a + b) / 2 < EPSILON) {
                    // [x, b]
                    ans = new Pair<>(null, new Pair<>(x, b));
                    u = x + t * (b - x);
                    e = b - x;
                } else {
                    // [a, x]
                    ans = new Pair<>(null, new Pair<>(a, x));
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
