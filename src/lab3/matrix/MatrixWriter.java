package lab3.matrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class MatrixWriter {
    private static final String[] FILES = {"au.txt", "al.txt", "ia.txt", "d.txt", "b.txt", "ja.txt"};

    /**
     * Transform lab3.matrix with symmetry profile into profile format and write it in files:
     * <ul>
     *     <li><var>al.txt</var>    row profile</li>
     *     <li><var>au.txt</var>    column profile</li>
     *     <li><var>b.txt</var>     vector b</li>
     *     <li><var>d.txt</var>     lab3.matrix diagonal</li>
     *     <li><var>ia.txt</var>    profiles' shifts for each row (column)</li>
     * </ul>
     * @param matrix        lab3.matrix with symmetry profile
     * @param path          output path
     * @throws IOException  If an I/O error occurs
     */
    public static void transformToProfileAndWrite(final double[][] matrix, final String... path) throws IOException {
        try {
            Files.createDirectories(Path.of(String.join(File.separator, path)));
        } catch (IOException e) {
            throw new IOException("Error in creating directories " + Path.of(String.join(File.separator, path) + ". " + e.getMessage()));
        }
        final int size = matrix.length;
        final double[] b = MatrixUtil.multiply(matrix, DoubleStream.iterate(1.0, x -> x + 1.0).limit(size).toArray());
        final double[] d = new double[size];
        final ArrayList<Double> al = new ArrayList<>();
        final ArrayList<Double> au = new ArrayList<>();
        final int[] ia = new int[size + 1];
        for (int i = 0; i < size; i++) {
            d[i] = matrix[i][i];
        }
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 1; i < size; i++) {
            int ind = 0;
            while (ind < i && matrix[i][ind] == 0) {
                ind++;
            }
            while (ind < i) {
                al.add(matrix[i][ind]);
                au.add(matrix[ind][i]);
                ind++;
            }
            ia[i + 1] = al.size();
        }
        for (final String fileName : FILES) {
            if (fileName.equals("ja.txt")) continue;
            try (final BufferedWriter out = Files.newBufferedWriter(Path.of(String.join(File.separator, path), fileName))) {
                switch (fileName) {
                    case "au.txt" -> out.write(au.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "al.txt" -> out.write(al.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "ia.txt" -> out.write(Arrays.stream(ia).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "d.txt" -> out.write(Arrays.stream(d).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "b.txt" -> out.write(Arrays.stream(b).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                }
            }
        }
    }

    /**
     * Transform symmetry lab3.matrix into sparse row-column format and write it in files:
     * <ul>
     *     <li><var>al.txt</var>    row profile</li>
     *     <li><var>au.txt</var>    column profile</li>
     *     <li><var>b.txt</var>     vector b</li>
     *     <li><var>d.txt</var>     lab3.matrix diagonal</li>
     *     <li><var>ia.txt</var>    profiles' shifts for each row (column)</li>
     *     <li><var>ja.txt</var>    columns' numbers which are kept in <var>al.txt</var> and <var>au.txt</var></li>
     * </ul>
     * @param matrix        симметричная матрица
     * @param path          output path
     * @throws IOException  If an I/O error occurs
     */
    public static void transformToRowColumnAndWrite(final double[][] matrix, final String... path) throws IOException {
        try {
            Files.createDirectories(Path.of(String.join(File.separator, path)));
        } catch (IOException e) {
            throw new IOException("Error in creating directories " + Path.of(String.join(File.separator, path) + ". " + e.getMessage()));
        }
        final int size = matrix.length;
        final double[] b = MatrixUtil.multiply(matrix, DoubleStream.iterate(1.0, x -> x + 1.0).limit(size).toArray());
        final double[] d = new double[size];
        final ArrayList<Double> al = new ArrayList<>();
        final ArrayList<Double> au = new ArrayList<>();
        final ArrayList<Integer> ja = new ArrayList<>();
        final int[] ia = new int[size + 1];
        for (int i = 0; i < size; i++) {
            d[i] = matrix[i][i];
        }
        ia[0] = 0;
        ia[1] = 0;
        for (int i = 1; i < size; i++) {
            int ind = 0;
            while (ind < i && matrix[i][ind] == 0) {
                ind++;
            }
            while (ind < i) {
                if (matrix[i][ind] != 0) {
                    al.add(matrix[i][ind]);
                    au.add(matrix[ind][i]);
                    ja.add(ind);
                }
                ind++;
            }
            ia[i + 1] = al.size();
        }
        for (final String fileName : FILES) {
            try (final BufferedWriter out = Files.newBufferedWriter(Path.of(String.join(File.separator, path), fileName))) {
                switch (fileName) {
                    case "au.txt" -> out.write(au.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "al.txt" -> out.write(al.stream().map(Object::toString).collect(Collectors.joining(" ")));
                    case "ia.txt" -> out.write(Arrays.stream(ia).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "d.txt" -> out.write(Arrays.stream(d).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "b.txt" -> out.write(Arrays.stream(b).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
                    case "ja.txt" -> out.write(ja.stream().map(Object::toString).collect(Collectors.joining(" ")));
                }
            }
        }
    }
}
