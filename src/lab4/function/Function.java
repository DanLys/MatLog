package lab4.function;

public interface Function {
    double apply(double[] x);
    double[] applyGrad(double[] x);
    double[] getAX(double[] x);
    double[][] getHessian(double[] x);
}
