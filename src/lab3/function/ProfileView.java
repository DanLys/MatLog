package lab3.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ProfileView {
    private double[] al;
    private double[] au;
    private double[] d;
    public int[] ia;
    boolean isLU;
    private static final String[] FILES = {"au.txt", "al.txt", "ia.txt", "d.txt"};

    public ProfileView(final String path) {
        this(path, false);
    }

    public ProfileView(final String pathOfMatrix, final boolean isLU) {
        this.isLU = isLU;
        read(pathOfMatrix);
    }

    /**
     * Reading Matrix from specified path
     *
     * @param path      path to directory, that contains files for lab3.matrix
     */
    private void read(final String path) {
        for (final String fileName : FILES) {
            try (final BufferedReader reader = Files.newBufferedReader(Path.of(path + File.separator + fileName))) {
                switch (fileName) {
                    case "au.txt" -> au = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "al.txt" -> al = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                    case "ia.txt" -> ia = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                    case "d.txt" -> d = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
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
     * @throws      IllegalStateException if lab3.matrix is in LU-view
     */
    public double getIJ(final int i, final int j) {
        if (isLU) {
            throw new IllegalStateException("Matrix is LU. Use getU, getL");
        }
        return getIJWithoutException(i, j);
    }

    /**
     * Get element form lab3.matrix A by indexes
     * @param i     row
     * @param j     column
     * @return      element a[i][j]
     */
    private double getIJWithoutException(final int i, final int j) {
        if (i == j) {
            return d[i];
        } else if (i < j) {
            return getFromUpperTriangle(i, j);
        } else {
            return getFromLowerTriangle(i, j);
        }
    }

    /**
     * Get element by indexes from lower triangle
     * @param i     row
     * @param j     column
     * @return      element from lower triangle with indexes i and j
     */
    public double getFromLowerTriangle(final int i, final int j) {
        return profileGet(i, j, al);
    }

    /**
     * Get element by indexes from upper triangle
     * @param i     row
     * @param j     column
     * @return      element from upper triangle with indexes i and j
     */
    public double getFromUpperTriangle(final int i, final int j) {
        return profileGet(j, i, au);
    }

    /**
     * Get element from specified profile
     * @param i         row
     * @param j         column
     * @param profile   specified profile
     * @return          element a[i][j]
     */
    private double profileGet(final int i, final int j, final double[] profile) {
        if (i == j) {
            throw new IllegalStateException("Coordinate from diag " + i + " " + j);
        }
        if (j > i) {
            return 0;
        }
        final int realCount = ia[i + 1] - ia[i];
        final int imagineCount = i - realCount;
        if (j < imagineCount) {
            return 0;
        } else {
            return profile[ia[i] + j - imagineCount];
        }
    }

    /**
     * Set element in specified profile
     * @param i         row
     * @param j         column
     * @param value     setting value
     * @param profile   profile
     */
    private void setProfile(final int i, final int j, final double value, final double[] profile) {
        final int realCount = ia[i + 1] - ia[i];
        final int imagineCount = i - realCount;
        if (j >= imagineCount) {
            profile[ia[i] + j - imagineCount] = value;
        }
    }

    /**
     * Asserting that lab3.matrix is in LU-view
     */
    private void assertLU() {
        if (!isLU) {
            throw new IllegalStateException("Matrix isn't in LU");
        }
    }

    /**
     * Get element from L lab3.matrix (A = LU) by indexes
     * @param i     row
     * @param j     column
     * @return      element l[i][j]
     */
    public double getFromL(final int i, final int j) {
        assertLU();
        if (i == j) {
            return d[i];
        } else {
            return getFromLowerTriangle(i, j);
        }
    }

    /**
     * Get element from U lab3.matrix (A = LU) by indexes
     *
     * @param i     row
     * @param j     column
     * @return      element u[i][j]
     */
    public double getFromU(final int i, final int j) {
        assertLU();
        if (i == j) {
            return 1;
        } else {
            return getFromUpperTriangle(i, j);
        }
    }

    /**
     * Set element in L
     * @param i         row
     * @param j         column
     * @param value     setting value
     */
    public void setL(final int i, final int j, final double value) {
        assertLU();
        if (i == j) {
            d[i] = value;
            return;
        }
        if (i < j) {
            return;
        }
        setProfile(i, j, value, al);
    }

    /**
     * Set element in U
     * @param i         row
     * @param j         column
     * @param value     setting value
     */
    public void setU(final int i, final int j, final double value) {
        assertLU();
        if (i >= j) {
            return;
        }
        setProfile(j, i, value, au);
    }

    /**
     * Transform lab3.matrix into LU-view
     */
    public void transformToLU() {
        if (isLU) {
            return;
        }
        isLU = true;
        setL(0, 0, getIJWithoutException(0, 0));
        for (int i = 1; i < size(); i++) {
            //setting L_ij
            for (int j = 0; j < i; j++) {
                double subtract = 0;
                for (int k = 0; k < j; k++) {
                    subtract += getFromL(i, k) * getFromU(k, j);
                }
                setL(i, j, getIJWithoutException(i, j) - subtract);
            }
            //setting U_ji
            for (int j = 0; j < i; j++) {
                double subtract = 0;
                for (int k = 0; k < j; k++) {
                    subtract += getFromL(j, k) * getFromU(k, i);
                }
                setU(j, i, (getIJWithoutException(j, i) - subtract) / getFromL(j, j));
            }
            //setting L_ii
            double subtract = 0;
            for (int k = 0; k < i; k++) {
                subtract += getFromL(i, k) * getFromU(k, i);
            }
            setL(i, i, getIJWithoutException(i, i) - subtract);
            //setting U_ii
            setU(i, i, 1);
        }
    }

    /**
     * @return      string representation of lab3.matrix
     */
    @Override
    public String toString() {
        return "Matrix{" +
                "al=" + Arrays.toString(al) +
                ", au=" + Arrays.toString(au) +
                ", d=" + Arrays.toString(d) +
                ", ia=" + Arrays.toString(ia) +
                '}';
    }

    /**
     * Writing lab3.matrix in "square" format in System.out
     */
    public void print() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                System.out.print(getIJ(i, j) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Writing L part of Matrix in "square" format in System.out
     */
    public void printL() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                System.out.print(getFromL(i, j) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Writing U part of Matrix in "square" format in System.out
     */
    public void printU() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                System.out.print(getFromU(i, j) + " ");
            }
            System.out.println();
        }
    }
}
