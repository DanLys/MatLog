package lab4.quasi;

import lab4.function.Function;
import lab4.search.MethodCombinationOfBrent;

import java.util.Arrays;
import java.util.stream.Collectors;

import static lab4.matrix.MatrixUtil.*;

public class FPDMethod extends AbstractQuasiNewton {

    public FPDMethod(double epsilon) {
        super(epsilon);
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        iter = 0;
        print(x0);
        double[][] prevA = generateUnaryMatrix(x0.length);
        double[] prevGrad = multiply(function.applyGrad(x0), -1.0);
        double[] prevP = prevGrad;
        double ak = getAlpha(function, x0, prevP);
        double[] prevX = add(x0, multiply(prevP, ak));
        double[] prevDX = subtract(prevX, x0);
        while (norm(prevDX) > EPS) {
            double[] nextGrad = multiply(function.applyGrad(prevX), -1.0);
            double[][] nextA;
            nextA = iterationProcess(prevA, prevDX, prevGrad, nextGrad);
            double[] nextP = multiply(nextA, nextGrad);
            ak = getAlpha(function, prevX, nextP);
            double[] nextX = add(prevX, multiply(nextP, ak));
            print(nextX);
            prevA = nextA;
            prevGrad = nextGrad;
            prevDX = subtract(nextX, prevX);
            prevX = nextX;
            iter++;
        }
        System.out.println("\n(" + Arrays.stream(prevX).mapToObj(Double::toString).collect(Collectors.joining(", ")) + ")");
        System.out.println(iter + "\n");
        return prevX;
    }

    /**
     * Iteration process
     * @param prevA     previous step iteration matrix
     * @param prevDX    previous step difference in x
     * @param prevGrad  previous step gradient value
     * @param nextGrad  current step gradient value
     * @return          current step iteration matrix
     */
    private double[][] iterationProcess(final double[][] prevA, final double[] prevDX, final double[] prevGrad, final double[] nextGrad) {
        double[] dw = subtract(nextGrad, prevGrad);
        double[] v = multiply(prevA, dw);
        return subtract(subtract(prevA ,multiply(multiply(prevDX,prevDX), 1 / scalarProduct(dw, prevDX))), multiply(multiply(v, v), 1 / scalarProduct(v, dw)));
    }

    /** Get alpha for current iteration
     *
     * @param function  optimized function
     * @param x         current solution
     * @param p         current direction
     * @return          alpha for current iteration
     */
    private double getAlpha(Function function, final double[] x, final double[] p) {
        return new MethodCombinationOfBrent(
                alpha -> function.apply(add(x, multiply(p, alpha))),
                -10d,
                10d,
                EPS
        ).getMinimalValue();
    }

}
