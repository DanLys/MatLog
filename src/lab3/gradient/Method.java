package lab3.gradient;

import lab3.function.Function;

public interface Method {
    double[] findMin(Function function, double[] x0);
    int getIter();
}
