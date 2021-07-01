package lab4.newton;

import lab4.search.MethodCombinationOfBrent;
import lab4.function.Function;
import lab4.solver.GaussSolver;
import lab4.solver.Solver;

import java.util.Arrays;
import java.util.stream.Collectors;

import static lab4.matrix.MatrixUtil.*;

public class NewtonWithOneDimensionSearch implements NewtonMethod {
    private final Solver solver;
    private final double EPS;

    public NewtonWithOneDimensionSearch(double epsilon) {
        this.solver = new GaussSolver();
        this.EPS = epsilon;
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        int iter = 1;
        double[] prevX = x0;
        double[] p = solver.solve(function.getHessian(prevX), multiply(function.applyGrad(prevX), -1), EPS);
        double[] curX = add(prevX, p);
        print(x0);
        print(curX);
        while (norm(subtract(curX, prevX)) > EPS && norm(p) > EPS) {
            prevX = curX;
            p = solver.solve(function.getHessian(prevX), multiply(function.applyGrad(prevX), -1), EPS);
            double[] finalPrevX = prevX;
            double[] finalP = p;
            java.util.function.Function<Double, Double> f = alpha -> function.apply(add(finalPrevX, multiply(finalP, alpha)));
            double alpha = new MethodCombinationOfBrent(f, -100, 100, EPS).getMinimalValue();
            curX = add(prevX, multiply(p, alpha));
//            System.out.println(iter + " : " + alpha);
            print(curX);
            iter++;
        }
        System.out.println("\n(" + Arrays.stream(curX).mapToObj(Double::toString).collect(Collectors.joining(", ")) + ")");
        System.out.println(iter + "\n");
        return curX;
    }

}
