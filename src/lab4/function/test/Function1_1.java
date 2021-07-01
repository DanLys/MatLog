package lab4.function.test;

import lab4.function.Function;
import lab4.function.SquareFunction;

public class Function1_1 implements Function {
    private static final Function function = new SquareFunction(new double[][]{{6, 6}, {6, 12}}, new double[]{-6.0, 0.0}, 5.0);

    @Override
    public double apply(double[] x) {
        return function.apply(x);
    }

    @Override
    public double[] applyGrad(double[] x) {
        return function.applyGrad(x);
    }

    @Override
    public double[] getAX(double[] x) {
        return function.getAX(x);
    }

    @Override
    public double[][] getHessian(double[] x) {
        return function.getHessian(x);
    }
}
