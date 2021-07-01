package lab4.function.test;

import lab4.function.Function;

public class Function3_2 implements Function {
    @Override
    public double apply(double[] x) {
        return sqr(sqr(x[0]) + x[1] - 11) + sqr(x[0] + sqr(x[1]) - 7);
    }

    @Override
    public double[] applyGrad(double[] x) {
        double[] ans = new double[2];
        ans[0] = findGrad1(x);
        ans[1] = findGrad2(x);
        return ans;
    }

    private double findGrad2(double[] x) {
        return 2 * (sqr(x[0]) + 2 * x[1] * (x[0] + sqr(x[1]) - 7) + x[1] - 11);
    }

    private double findGrad1(double[] x) {
        return 2 * (2 * x[0] * (sqr(x[0]) + x[1] - 11) + x[0] + sqr(x[1]) - 7);
    }

    @Override
    public double[] getAX(double[] x) {
        return new double[0];
    }

    @Override
    public double[][] getHessian(double[] x) {
        double[][] H = new double[2][2];
        H[0][0] = findH11(x);
        H[0][1] = H[1][0] = findH12(x);
        H[1][1] = findH22(x);
        return H;
    }

    private double findH11(double[] x) {
        return 12 * sqr(x[0]) + 4 * x[1] - 42;
    }

    private double findH12(double[] x) {
        return 4 * (x[0] + x[1]);
    }

    private double findH22(double[] x) {
        return 2 * (1 + 2 * ((x[0] + sqr(x[1]) - 7) + 2 * sqr(x[1])));
    }

    private double sqr(double a) {
        return a * a;
    }
}
