package lab4.newton;

import lab4.function.Function;
import lab4.search.MethodCombinationOfBrent;
import lab4.solver.GaussSolver;
import lab4.solver.Solver;

import java.util.Arrays;
import java.util.stream.Collectors;

import static lab4.matrix.MatrixUtil.*;

public class NewtonWithDescentDirection implements NewtonMethod {
    private final Solver solver;
    private final double EPS;

    public NewtonWithDescentDirection(double epsilon) {
        this.solver = new GaussSolver();
        this.EPS = epsilon;
    }

    @Override
    public double[] findMin(Function function, double[] x0) {
        int iter = 1;
        double[] antiGrad0 = multiply(function.applyGrad(x0), -1.0);
        java.util.function.Function<Double, Double> f0 = alpha -> function.apply(add(x0, multiply(antiGrad0, alpha)));
        double alpha0 = new MethodCombinationOfBrent(f0, 0.0, 100.0, EPS).getMinimalValue();
        double[] curX = add(x0, multiply(antiGrad0, alpha0));
        print(x0);
        print(curX);
        double diff = norm(subtract(curX, x0));
        while(diff > EPS) {
            double[] prevX = curX;
            double[] gradient = function.applyGrad(prevX);
            double[] antiGradient = multiply(gradient, -1);
            double[] p = solver.solve(function.getHessian(prevX), antiGradient, EPS);
            final double[] direction = scalarProduct(p, gradient) >= 0 ? antiGradient : p;
            if (norm(direction) < EPS) {
                break;
            }
            java.util.function.Function<Double, Double> f = alpha -> function.apply(add(prevX, multiply(direction, alpha)));
            double alpha = new MethodCombinationOfBrent(f, -100, 100, EPS).getMinimalValue();
//            System.out.println(iter + " : " + alpha);
            curX = add(prevX, multiply(direction, alpha));
            print(curX);
//            print(prevX, curX);
//            System.out.println("(" + Arrays.stream(curX).mapToObj(Double::toString).collect(Collectors.joining(", ")) + ")");
            diff = norm(subtract(curX, prevX));
            iter++;
        }
        System.out.println("\n(" + Arrays.stream(curX).mapToObj(Double::toString).collect(Collectors.joining(", ")) + ")");
        System.out.println(iter + "\n");
        return curX;
    }
}
