package lab4.solver;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GaussSolver implements Solver {
    private double[][] matrix;
    private double[] b;
    private int[] columnPermutation;
    private int[] rowPermutation;
    private int n;

    /**
     * Transform matrix into diagonal view by using max element on current step
     */
    private void diagonalized(double epsilon) {
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
            if (Math.abs(matrix[rowPermutation[i]][columnPermutation[i]]) < epsilon) {
                continue;
            }
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
    public double[] solve(double epsilon) {
        diagonalized(epsilon);
        double[] x = new double[n];
        for (int i = 0; i < n; ++i) {
            if (Math.abs(matrix[rowPermutation[i]][columnPermutation[i]]) < epsilon && Math.abs(b[rowPermutation[i]]) < epsilon) {
                x[columnPermutation[i]] = 0.0;
            } else {
                x[columnPermutation[i]] = b[rowPermutation[i]] / matrix[rowPermutation[i]][columnPermutation[i]];
            }
        }
        return x;
    }

    @Override
    public double[] solve(double[][] A, double[] B, double epsilon) {
        this.matrix = Arrays.stream(A).map(row -> Arrays.copyOf(row, row.length)).toArray(double[][]::new);
        this.b = Arrays.copyOf(B, B.length);
        n = A.length;
        rowPermutation = IntStream.range(0, n).toArray();
        columnPermutation = IntStream.range(0, n).toArray();
        return solve(epsilon);
    }
}
