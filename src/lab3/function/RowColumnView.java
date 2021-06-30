package lab3.function;

import static lab3.matrix.MatrixUtil.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class RowColumnView implements Function {
    private double[] al;
    private double[] au;
    private double[] d;
    private double[] b;
    private int[] ia;
    private int[] ja;
    private static final String[] NAME_OF_FILES = {"au.txt", "al.txt", "ia.txt", "d.txt", "ja.txt", "b.txt"};

    public RowColumnView(final String pathOfMatrixAndVector) {
        for (final String fileName : NAME_OF_FILES) {
            try (final BufferedReader reader = Files.newBufferedReader(Path.of(pathOfMatrixAndVector + File.separator + fileName))) {
                switch (fileName) {
                    case "au.txt" -> au = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "al.txt" -> al = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "ia.txt" -> ia = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    case "ja.txt" -> ja = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    case "d.txt" -> d = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "b.txt" -> b = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get lab3.matrix size
     * @return      lab3.matrix size
     */
    public int size() {
        return d.length;
    }

    /**
     * Get element form lab3.matrix by indexes
     * @param i     row
     * @param j     column
     * @return      element a[i][j]
     */
    public double getIJ(final int i, final int j) {
        if (i == j) {
            return d[i];
        }
        if (i > j) {
            return getFromLowerTriangle(i, j);
        } else {
            return getFromUpperTriangle(i, j);
        }
    }

    /**
     * Get element by indexes from lower triangle
     * @param i     row
     * @param j     column
     * @return      element from lower triangle with indexes i and j
     */
    private double getFromLowerTriangle(final int i, final int j) {
        return getFromTriangle(i, j, true);
    }

    /**
     * Get element by indexes from upper triangle
     * @param i     row
     * @param j     column
     * @return      element from upper triangle with indexes i and j
     */
    private double getFromUpperTriangle(final int i, final int j) {
        return getFromTriangle(j, i, false);
    }

    /**
     * Get element from lab3.matrix by indexes with additional arrays
     * @param i     row
     * @param j     column
     * @param flag  flag which shows which array we should use:
     *              <ul>
     *                  <li><var>true</var>    element from lower triangle</li>
     *                  <li><var>false</var>   element from upper triangle</li>
     *              </ul>
     * @return      element with indexes i and j
     */
    private double getFromTriangle(final int i, final int j, final boolean flag) {
        if (i <= j) {
            throw new IllegalArgumentException("Not Triangle indexes i = " + (flag ? i : j) + "; column = " + (!flag ? i : j) + ";");
        }
        final int realInJA = ia[i + 1] - ia[i];
        int offset = 0;
        for (; offset < realInJA; offset++) {
            if (ja[ia[i] + offset] == j) {
                break;
            } else if (ja[ia[i] + offset] > j) {
                return 0;
            }
        }
        if (offset == realInJA) {
            return 0;
        }
        if (flag) {
            return al[ia[i] + offset];
        } else {
            return au[ia[i] + offset];
        }
    }

    @Override
    public double apply(final double[] x) {
        final double[] a = getAX(x);
        final double quad = scalarProduct(x, a) / 2;
        final double one = scalarProduct(b, x);

        return quad - one;
    }

    @Override
    public double[] applyGrad(final double[] x) {
        return subtract(getAX(x), b);
    }

    @Override
    public double[] getAX(final double[] x) {
        final double[] ans = new double[size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                ans[i] += getIJ(i, j) * x[j];
            }
        }
        return ans;
    }

    /**
     * Get vector B
     * @return      vector B
     */
    public double[] getB() {
        return b;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RowColumnMatrix{");
        sb.append(System.lineSeparator());
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                sb.append(getIJ(i, j)).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("}");
        return sb.toString();
    }
}
