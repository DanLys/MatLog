package method.optimisation.data;
public abstract class AbstractComparator {        // need tests
    protected final Double EPS = 1e-7;
    // x < y <=> x - y < 0 <=> x - y < eps
    public boolean isLess(double x, double y) {
        return x - y < EPS;
    }
    public boolean isBigger(double x, double y) {
        return x - y > EPS;
    }
    public boolean isLessOrEqual(double x, double y) {
        return x - y <= EPS;
    }
    public boolean isBiggerOrEqual(double x, double y) {
        return x - y >= EPS;
    }
    public boolean isEqual(double x, double y) {
        return Math.abs(x - y) <= EPS;
    }
}
