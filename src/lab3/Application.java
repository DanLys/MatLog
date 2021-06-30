package lab3;

import lab3.function.ProfileView;
import lab3.function.RowColumnView;
import lab3.gradient.ConjugateGradient;
import lab3.gradient.Method;
import lab3.matrix.MatrixGenerator;
import lab3.matrix.MatrixWriter;
import lab3.solver.LUSolver;

import static lab3.matrix.MatrixUtil.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Application {
    /**
     * Check command line arguments
     * @param args  arguments
     */
    private static void checkArgs(final String[] args) {
        if (args == null || Arrays.stream(args).anyMatch(Objects::isNull) || args.length == 0) {
            throw new IllegalArgumentException("Args shouldn't be null and have length > 0");
        }
        Path.of(args[0]);
        if (args.length > 1) {
            final String message = "If you give more than 1 args, second must be hilbert $dimension | ordinary $dimension $ordinary";
            args[1] = args[1].toLowerCase();
            if (args[1].equals("hilbert") && args.length == 3) {
                try {
                    Integer.parseInt(args[2]);
                } catch (final NumberFormatException ignored) {
                    throw new IllegalArgumentException(message);
                }
            } else if (args[1].equals("ordinary") && args.length == 4) {
                try {
                    Integer.parseInt(args[2]);
                    Integer.parseInt(args[3]);
                } catch (final NumberFormatException ignored) {
                    throw new IllegalArgumentException(message);

                }
            } else if (args[1].equals("bonus")) {
            } else {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * Read free members of equations from path
     * @param path  path with vector B
     * @return      vector <var>B</var>
     */
    private static double[] readB(final String path) {
        try (final BufferedReader reader = Files.newBufferedReader(Path.of(path, "b.txt"))) {
            return Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        } catch (final IOException e) {
            throw new IllegalArgumentException("Error in getting vector b from 'Ax = b' " + e.getMessage());
        }
    }

    /**
     * Solve system of linear equations
     * @param args  command line arguments
     */
    private static void solve(final String[] args) {
        //args: path [hilbert $dimension | ordinary $dimension $ordinary | bonus]
        checkArgs(args);
        boolean bonus = false;
        if (args.length > 1) {
            try {
                switch (args[1]) {
                    case "hilbert" -> MatrixWriter.transformToProfileAndWrite(MatrixGenerator.generateHilbertMatrix(Integer.parseInt(args[2])), args[0]);
                    case "ordinary" -> MatrixWriter.transformToProfileAndWrite(MatrixGenerator.generateOrdinaryMatrix(Integer.parseInt(args[2]), Integer.parseInt(args[3])), args[0]);
                    case "bonus" -> bonus = true;
                }
            } catch (final IOException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
        final double[] b, ans;
        if (bonus) {
            ans = null;

            b = solveBonus(args[0]);
        } else {
            b = readB(args[0]);
            final ProfileView matrix = new ProfileView(args[0]);
            LUSolver.solve(matrix, b);

//            final double[][] lab3.matrix = MatrixGenerator.generateHilbertMatrix(Integer.parseInt(args[2]));
//            try {
//                MatrixWriter.transformToProfileAndWrite(lab3.matrix, args[0]);
//            } catch (final IOException e) {
//                System.err.println(e.getMessage());
//                return;
//            }
//            ans = new GaussSolver(lab3.matrix, readB(args[0])).solve();
        }
        final double[] xReal = DoubleStream.iterate(1.0, x -> x + 1.0).limit(Integer.parseInt(args[2])).toArray();
        final double diff = norm(subtract(xReal, b));

//        final double diff = norm(subtract(xReal, ans));
        try (final BufferedWriter writer = Files.newBufferedWriter(Path.of(args[0], "ans.txt"))) {
            writer.write(Arrays.stream(b).mapToObj(Objects::toString).collect(Collectors.joining(" ")));

//            writer.write(Arrays.stream(ans).mapToObj(Objects::toString).collect(Collectors.joining(" ")));
            writer.write("\nDiff X : " + diff + "\nDiff / realX : " + (diff / norm(xReal)));
        } catch (final IOException e) {
            System.err.println("Error in writing answer. " + e.getMessage());
        }
    }

    /**
     * Solve system of linear equations with using {@link ConjugateGradient} method
     * @param path  path with system of linear equations
     * @return      solution
     */
    private static double[] solveBonus(final String path) {
        final RowColumnView matrix = new RowColumnView(path);
        final Method m = new ConjugateGradient(1e-7);
        final double[] xSolved = m.findMin(matrix, new double[matrix.size()]);
        final double[] xReal = DoubleStream.iterate(1.0, x -> x + 1.0).limit(xSolved.length).toArray();
        final double miss = norm(subtract(xReal, xSolved));
        System.out.println(m.getIter());
        System.out.println(miss);
        System.out.println(miss / norm(xReal));
        final double[] f = matrix.getB();
        final double missF = norm(subtract(f, matrix.getAX(xSolved)));
        System.out.println((miss / norm(xReal)) / (missF / norm(f)));
        return xSolved;
    }

    /**
     * Usage: path [hilbert $dimension | ordinary $dimension $ordinary | bonus]
     */
    public static void main(String[] args) {
//        solve(args);

        final int[] dimensions = {10, 100, 1000};
        for(int i : dimensions){
            final String path = "lab3/data/row-column/reversedSign/" + i;
            double[] x = solveBonus(path);
            System.out.println("-".repeat(10));
        }

    }
}
