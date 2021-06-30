package lab3.function;

public interface Function {
    double apply(double[] x);
    double[] applyGrad(double[] x);
    double[] getAX(double[] x);
}
