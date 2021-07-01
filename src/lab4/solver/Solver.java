package lab4.solver;

public interface Solver {

    /**
     * Solve system of linear equations Ax = B
     * @param A     coefficients
     * @param B     free members
     * @return      solution
     */
    double[] solve(double[][] A, double[] B, double epsilon);
}
