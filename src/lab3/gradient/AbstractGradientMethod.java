package lab3.gradient;

import java.util.Arrays;
import java.util.Locale;

public abstract class AbstractGradientMethod implements Method {
    protected final double EPS;
    protected int iter;

    public AbstractGradientMethod(final double EPS) {
        this.EPS = EPS;
        this.iter = 1;
    }

    protected void print(final double[] x, final double[] grad) {
        System.out.printf("%s : %s", Arrays.toString(x), Arrays.toString(grad));
    }

    protected void print(final double[] x1, final double[] x2, final double fX) {
        System.out.printf(Locale.US, "vector(%s,%s) : f(X) = %.5f\n", pointToString(x1), pointToString(x2), fX);
    }

    private String pointToString(final double[] x) {
        return String.format(Locale.US,"(%.6f;%.6f)", x[0], x[1]);
    }

    abstract protected String getName();

    public String toString() {
        return String.format("–%s:\n Iterations: %d", getName(), iter);
    }

    public int getIter() {
        return this.iter;
    }
}
