package lab4.newton;

import lab4.function.Function;

public interface NewtonMethod {
    double[] findMin(Function function, double[] x0);
}
