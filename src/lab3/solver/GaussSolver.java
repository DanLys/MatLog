package lab3.solver;

import java.util.stream.IntStream;

public class GaussSolver {
    private final double[][] matrix;
    private final double[] b;
    private final int[] columnPermutation;
    private final int[] rowPermutation;
    private final int n;

    public GaussSolver(final double[][] matrix, final double[] b) {
        this.matrix = matrix;
        this.b = b;
        n = matrix.length;
        rowPermutation = IntStream.range(0, n).toArray();
        columnPermutation = IntStream.range(0, n).toArray();
    }

    /**
     * Transform lab3.matrix into diagonal view by using max element on current step
     */
    private void diagonalized() {
        for (int i = 0; i < n; i++) {
            double maxElement = Math.abs(matrix[rowPermutation[i]][columnPermutation[i]]);
            int row = i;
            int col = i;
            for (int j = i; j < n; ++j) {
                for (int k = i; k < n; ++k) {
                    if (j == i && k == i) {
                        continue;
                    }
                    double elem = Math.abs(matrix[rowPermutation[j]][columnPermutation[k]]);
                    if (elem >= maxElement) {
                        maxElement = elem;
                        row = j;
                        col = k;
                    }
                }
            }
            int tmp = rowPermutation[i];
            rowPermutation[i] = rowPermutation[row];
            rowPermutation[row] = tmp;
            tmp = columnPermutation[i];
            columnPermutation[i] = columnPermutation[col];
            columnPermutation[col] = tmp;
            for (int j = 0; j < n; ++j) {
                if (j == i) continue;
                double delta = -(matrix[rowPermutation[j]][columnPermutation[i]]
                        / matrix[rowPermutation[i]][columnPermutation[i]]);
                matrix[rowPermutation[j]][columnPermutation[i]] = 0.0;
                for (int k = i + 1; k < n; ++k) {
                    matrix[rowPermutation[j]][columnPermutation[k]] += delta
                            * matrix[rowPermutation[i]][columnPermutation[k]];
                }
                b[rowPermutation[j]] += delta * b[rowPermutation[i]];
            }
        }
    }

    /**
     * Solve system of linear equations
     * @return      solution – vector <var>X</var>
     */
    public double[] solve() {
        diagonalized();
        double[] x = new double[n];
        for (int i = 0; i < n; ++i) {
            x[columnPermutation[i]] = b[rowPermutation[i]] / matrix[rowPermutation[i]][columnPermutation[i]];
        }
        return x;
    }
}
