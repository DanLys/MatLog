package lab4.newton;

import lab4.function.Function;
import lab4.solver.GaussSolver;
import lab4.solver.Solver;

import java.util.Arrays;
import java.util.stream.Collectors;

import static lab4.matrix.MatrixUtil.*;

public class NewtonClassic implements NewtonMethod {
    private final Solver solver;
    private final double EPS;

    public NewtonClassic(double epsilon){
        this.solver = new GaussSolver();
        this.EPS = epsilon;
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        int iter = 0;
        double[] curX = x0;
        double diff;
        print(curX);
        do {
            double[] prevX = curX;
            double[] p = solver.solve(function.getHessian(prevX), multiply(function.applyGrad(prevX), -1), EPS);
            curX = add(prevX, p);
            print(curX);
            diff = norm(subtract(curX, prevX));
            iter++;
        } while(diff > EPS);
        System.out.println("\n(" + Arrays.stream(curX).mapToObj(Double::toString).collect(Collectors.joining(", ")) + ")");
        System.out.println(iter + "\n");
        return curX;
    }
}
