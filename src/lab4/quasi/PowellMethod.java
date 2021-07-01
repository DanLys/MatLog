package lab4.quasi;

import lab4.function.Function;

import java.util.Arrays;
import java.util.stream.Collectors;

import static lab4.matrix.MatrixUtil.*;

public class PowellMethod extends AbstractQuasiNewton {

    public PowellMethod(double epsilon) {
        super(epsilon);
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        iter = 0;
        print(x0);
        double[] x = Arrays.copyOf(x0, x0.length);
        double[][] A = generateUnaryMatrix(x0.length);
        double[] grad = multiply(function.applyGrad(x0), -1);
        do {
            x = iterationProcess(function, x, A, grad);
            A = generateUnaryMatrix(x0.length);
            grad = multiply(function.applyGrad(x), -1);
        } while (norm(grad) > EPS);
        System.out.println("\n(" + Arrays.stream(x).mapToObj(Double::toString).collect(Collectors.joining(", ")) + ")");
        System.out.println(iter + "\n");
        return x;
    }

    /**
     * Iteration process
     * @param function  optimized function
     * @param x         current solution
     * @param A         current iteration matrix
     * @param gradient  gradient
     * @return          solution for current iteration
     */
    private double[] iterationProcess(Function function, double[] x, double[][] A, double[] gradient) {
        int i = 0;
        while (norm(gradient) > EPS && i < gradient.length) {
            double[] p = multiply(A, gradient);
            double[] nextX = findNextX(function, x, p);
            print(nextX);
            double[] nextGradient = multiply(function.applyGrad(nextX), -1);
            double[] deltaX = subtract(nextX, x);
            double[] deltaW = subtract(nextGradient, gradient);
            A = getNextC(A, add(deltaX, multiply(A, deltaW)), deltaW);
            x = nextX;
            gradient = nextGradient;
            i++;
        }
        iter += i;
        return x;
    }

    /**
     * Get next approximation for iteration process
     * @param C         current approximations
     * @param deltaX    approximation difference
     * @param deltaW    gradient difference
     * @return          next approximation
     */
    private double[][] getNextC(double[][] C, double[] deltaX, double[] deltaW) {
        double k = 1 / scalarProduct(deltaW, deltaX);
        return subtract(C, multiply(multiply(deltaX, deltaX), k));
    }
}
