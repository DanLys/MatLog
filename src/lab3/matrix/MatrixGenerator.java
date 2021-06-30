package lab3.matrix;

import java.util.Random;

public class MatrixGenerator {
    public static final Random RANDOM = new Random();

    /**
     * Generate square lab3.matrix with symmetry profile with conditions from lab3
     * @param n     lab3.matrix size
     * @param k     number
     * @return      generated lab3.matrix
     */
    public static double[][] generateOrdinaryMatrix(final int n, final int k) {
        final double[][] matrix = new double[n][n];
        final int[] profileOffset = new int[n];
        for (int i = 0; i < n; ++i) {
            profileOffset[i] = RANDOM.nextInt(i + 1);
        }
        for (int row = 0; row < n; ++row) {
            for (int col = profileOffset[row]; col < row; ++col) {
                matrix[row][col] = -RANDOM.nextInt(4) - 1;
            }
        }
        for (int col = 0; col < n; ++col) {
            for (int row = profileOffset[col]; row < col; ++row) {
                matrix[row][col] = -RANDOM.nextInt(4) - 1;
            }
        }
        for (int i = 0; i < n; ++i) {
            double sum = 0;
            for (int j = 0; j < n; ++j) {
                if (j != i) {
                    sum -= matrix[i][j];
                }
            }
            matrix[i][i] = sum;
            if (i == 0) {
                matrix[i][i] += Math.pow(0.1, k);
            }
        }
        return matrix;
    }

    /**
     * Generate diagonally dominant lab3.matrix with conditions from lab 3
     * @param n     lab3.matrix size
     * @return      generated lab3.matrix
     */
    public static double[][] generateDiagonalDominationMatrix(final int n) {
        final double[][] matrix = new double[n][n];
        final int[] profileOffset = new int[n];
        for (int i = 0; i < n; ++i) {
            profileOffset[i] = RANDOM.nextInt(i + 1);
        }
        for (int row = 0; row < n; ++row) {
            for (int col = profileOffset[row]; col < row; ++col) {
                int val = -RANDOM.nextInt(4) - 1;
                matrix[row][col] = val;
                matrix[col][row] = val;
            }
        }
        for (int i = 0; i < n; ++i) {
            double sum = 0;
            for (int j = 0; j < n; ++j) {
                if (j != i) {
                    sum -= matrix[i][j];
                }
            }
            matrix[i][i] = sum;
            if (i == 0) {
                matrix[i][i] += 1;
            }
        }
        return matrix;
    }

    /**
     * Generate Hilbert lab3.matrix
     * @param n     lab3.matrix size
     * @return      generated lab3.matrix
     */
    public static double[][] generateHilbertMatrix(final int n) {
        final double[][] matrix = new double[n][n];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                matrix[i - 1][j - 1] = (double) 1 / (i + j - 1);
            }
        }
        return matrix;
    }
}
