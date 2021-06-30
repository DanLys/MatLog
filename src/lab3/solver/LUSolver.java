package lab3.solver;

import lab3.function.ProfileView;

public class LUSolver {
    /**
     * Solve system of linear equations with Gauss forward method with lower triangle
     * @param matrix {@link ProfileView}    lower triangle
     * @param b                             free members of equations
     */
    private static void gaussForward(final ProfileView matrix, final double[] b) {
        for (int i = 0; i < matrix.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                b[i] -= b[j] * matrix.getFromL(i, j);
            }
            b[i] /= matrix.getFromL(i, i);
        }
    }

    /**
     * Solve system of linear equations with Gauss backward method with upper triangle
     * @param matrix {@link ProfileView}    upper triangle
     * @param y                             free members of equations
     */
    private static void gaussBackward(final ProfileView matrix, final double[] y) {
        for (int i = matrix.size() - 1; i >= 0; --i) {
            for (int j = matrix.size() - 1; j > i; --j) {
                y[i] -= y[j] * matrix.getFromU(i, j);
            }
            y[i] /= matrix.getFromU(i, i);
        }
    }


    /**
     * Solve system of linear equations
     * @param matrix {@link ProfileView}    lab3.matrix with coefficients
     * @param b                             free members of equations
     */
    public static void solve(final ProfileView matrix, final double[] b) {
        matrix.transformToLU();
        gaussForward(matrix, b);
        gaussBackward(matrix, b);
    }
}
