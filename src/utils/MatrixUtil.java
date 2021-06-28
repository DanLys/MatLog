package utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MatrixUtil {

    /**
     * Vector add vector
     * @param v1    the first vector
     * @param v2    the second vector
     * @return      vector of v1 + v2
     */
    public static double[] add(final double[] v1, final double[] v2) {
        double[] ans = new double[v1.length];
        IntStream.range(0, v1.length).forEach(i -> ans[i] = v1[i] + v2[i]);
        return ans;
    }

    /**
     * Matrix add matrix
     * @param m1    the first matrix
     * @param m2    the second matrix
     * @return      vector of m1 + m2
     */
    public static double[][] add(final double[][] m1, final double[][] m2) {
        double[][] ans = new double[m1.length][m1[0].length];
        IntStream.range(0, m1.length).forEach(i -> ans[i] = add(m1[i], m2[i]));
        return ans;
    }

    /**
     * Vector subtract vector
     * @param v1    the first vector
     * @param v2    the second vector
     * @return      vector of v1 - v2
     */
    public static double[] subtract(final double[] v1, final double[] v2) {
        double[] ans = new double[v1.length];
        IntStream.range(0, v1.length).forEach(i -> ans[i] = v1[i] - v2[i]);
        return ans;
    }

    /**
     * Matrix subtract matrix
     * @param m1    the first matrix
     * @param m2    the second matrix
     * @return      vector of m1 - m2
     */
    public static double[][] subtract(final double[][] m1, final double[][] m2) {
        double[][] ans = new double[m1.length][m1[0].length];
        IntStream.range(0, m1.length).forEach(i -> ans[i] = subtract(m1[i], m2[i]));
        return ans;
    }

    /** Scalar product of two vectors
     * @param v1    the first vector
     * @param v2    the second vector
     * @return      result of scalar product : (v1, v2)
     */
    public static double scalarProduct(final double[] v1, final double[] v2) {
        double ans = 0.0;
        for (double v : dotProduct(v1, v2)) {
            ans += v;
        }
        return ans;
    }

    /** Dot product of two vectors
     * @param v1    the first vector
     * @param v2    the second vector
     * @return      dot product of them
     */
    public static double[] dotProduct(final double[] v1, final double[] v2) {
        double[] ans = new double[v1.length];
        IntStream.range(0, v1.length).forEach(i -> ans[i] = v1[i] * v2[i]);
        return ans;
    }

    /** Vector product of two vectors
     * @param v1    the first vector
     * @param v2    the second vector
     * @return      result of vector product : v1 x v2
     */
    public static double[][] multiply(final double[] v1, final double[] v2) {
        double[][] ans = new double[v1.length][v1.length];
        for (int i = 0; i < v1.length; i++) {
            for (int j = 0; j < v1.length; j++) {
                ans[i][j] += v1[i] * v2[j];
            }
        }
        return ans;
    }

    /**
     * Multiply vector and number
     * @param v     vector
     * @param c     number
     * @return      result of their product
     */
    public static double[] multiply(final double[] v, final double c) {
        double[] ans = new double[v.length];
        IntStream.range(0, v.length).forEach(i -> ans[i] = v[i] * c);
        return ans;
    }

    /**
     * Multiply matrix and number
     * @param m     matrix
     * @param c     number
     * @return      result of their product
     */
    public static double[][] multiply(final double[][] m, final double c) {
        double[][] ans = new double[m.length][m[0].length];
        IntStream.range(0, m.length).forEach(i -> {
            IntStream.range(0, m.length).forEach(j -> ans[i][j] = m[i][j] * c);
        });
        return ans;
    }

    /**
     * Multiply matrix and vector
     * @param m     matrix
     * @param v     vector
     * @return      result of their product
     */
    public static double[] multiply(final double[][] m, final double[] v) {
        double[] ans = new double[m.length];
        IntStream.range(0, m.length).forEach(i -> {
            IntStream.range(0, m.length).forEach(j -> ans[i] += m[i][j] * v[j]);
        });
        return ans;
    }

    /**
     * Matrix product
     * @param m1    the first matrix
     * @param m2    the second matrix
     * @return      result of their product
     */
    public static double[][] multiply(final double[][] m1, final double[][] m2) {
        double[][] ans = new double[m1.length][m2.length];
        IntStream.range(0, m1.length).forEach(i ->
                IntStream.range(0, m2.length).forEach(j ->
                        IntStream.range(0, m2.length).forEach(k -> ans[i][j] += m1[i][k] * m2[k][j])));
        return ans;
    }

    /** Vector's norm
     * @param v     vector
     * @return      Euclidean norm of vector v
     */
    public static double norm(final double[] v) {
        double sum = 0.0;
        for (double value : v) {
            sum += Math.pow(value, 2);
        }
        return Math.sqrt(sum);
    }

    /** Generate diagonal matrix with define size and condition number
     * @param n     matrix's size
     * @param k     condition number
     * @return      diagonal matrix with parameters n and k
     */
    public static double[] generateDiagMatrix(int n, double k) {
        double[] diag = new double[n];
        diag[0] = 1.0;
        IntStream.range(1, n - 1).forEach(i -> diag[i] = (diag[i - 1] + 1) % k);
        diag[n - 1] = k;
        return diag;
    }

    /** Generate vector with define size
     * @param n     matrix's size
     * @param mod   module
     * @return      vector with size n and elements less then mod
     */
    public static double[] generateVector(int n, double mod) {
        double[] v = new double[n];
        IntStream.range(0, n).forEach(i -> v[i] = Math.random() * mod);
        return v;
    }

    /** Generate unary vector
     *
     */
    public static double[] generateVector(int n) {
        double[] v = new double[n];
        Arrays.fill(v, 1.0);
        return v;
    }
}