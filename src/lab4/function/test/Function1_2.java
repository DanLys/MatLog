package lab4.function.test;

import lab4.function.Function;

public class Function1_2 implements Function {
    @Override
    public double apply(double[] x) {
        return Math.pow((2 * x[0] + x[1] + 2), 4) + sqr(x[0] - 6 * x[1]);
    }

    @Override
    public double[] applyGrad(double[] x) {
        return new double[]{
                2 * (x[0] - 6 * x[1] + 4 * cube(2 + 2 * x[0] + x[1])),
                4 * (-3 * (x[0] - 6 * x[1]) + cube(2 + 2 * x[0] + x[1]))
        };
    }

    @Override
    public double[] getAX(double[] x) {
        return new double[0];
    }

    @Override
    public double[][] getHessian(double[] x) {
        return new double[][]{
                {48 * sqr(2 * x[0] + x[1] + 2) + 2, 24 * sqr(2 * x[0] + x[1] + 2) - 12},
                {24 * sqr(2 * x[0] + x[1] + 2) - 12, 12 * sqr(2 * x[0] + x[1] + 2) + 72}
        };
    }

    private double sqr(double a) {
        return a * a;
    }

    private double cube(double a) {
        return a * a * a;
    }
}
