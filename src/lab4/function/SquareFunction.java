package lab4.function;

import static lab4.matrix.MatrixUtil.*;

public class SquareFunction implements Function {

    private final double[][] A;
    private final double[] B;
    private final double C;

    public SquareFunction(double[][] a, double[] b, double c) {
        A = a;
        B = b;
        C = c;
    }

    @Override
    public double apply(double[] x) {
        return 0.5 * scalarProduct(getAX(x), x) + scalarProduct(B, x) + C;
    }

    @Override
    public double[] applyGrad(double[] x) {
        return add(getAX(x), B);
    }

    @Override
    public double[] getAX(double[] x) {
        return multiply(A, x);
    }

    @Override
    public double[][] getHessian(double[] x) {
        return A;
    }

}
