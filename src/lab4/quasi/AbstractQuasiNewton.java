package lab4.quasi;

import lab4.function.Function;
import lab4.newton.NewtonMethod;
import lab4.search.MethodCombinationOfBrent;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static lab4.matrix.MatrixUtil.*;

public abstract class AbstractQuasiNewton implements NewtonMethod {
    protected final double EPS;
    protected int iter = 0;

    protected AbstractQuasiNewton(double epsilon) {
        this.EPS = epsilon;
    }

    /**
     * Generate unary matrix with define dimension
     * @param n     dimension
     * @return      unary matrix with define dimension
     */
    protected double[][] generateUnaryMatrix(final int n) {
        return IntStream.range(0, n).mapToObj(i-> {
            double[] row = new double[n];
            row[i] = 1;
            return row;
        }).toArray(double[][]::new);
    }

    /**
     * Get next approximation
     * @param function      optimized function
     * @param x0            first approximation
     * @param p             direction
     * @return              next approximation
     */
    protected double[] findNextX(Function function, double[] x0, double[] p) {
        double a = findLinearMinimum(function, x0, p);
        p = multiply(p, a);
        double[] ans = add(x0, p);
        return ans;
    }

    /**
     * Find value of step by using Brent's method
     * @param function      optimized function
     * @param x             first approximation
     * @param p             direction
     * @return              value of step
     */
    protected double findLinearMinimum(Function function, double[] x, double[] p) {
        java.util.function.Function<Double, Double> f = a -> function.apply(add(x, multiply(p, a)));
        return new MethodCombinationOfBrent(f, 0, 10, EPS).getMinimalValue();
    }
}
