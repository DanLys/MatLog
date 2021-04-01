package method.optimisation;
public abstract class AbstractComparator {        // need tests
    protected static final Double EPS = 1e-7;
    // x < y <=> x - y < 0 <=> x - y < eps
    public static boolean isLess(double x, double y) {
        return x - y < EPS;
    }
    public static boolean isBigger(double x, double y) {
        return x - y > EPS;
    }
    public static boolean isLessOrEqual(double x, double y) {
        return x - y <= EPS;
    }
    public static boolean isBiggerOrEqual(double x, double y) {
        return x - y >= EPS;
    }
    public static boolean isEqual(double x, double y) {
        return Math.abs(x - y) <= EPS;
    }
}
