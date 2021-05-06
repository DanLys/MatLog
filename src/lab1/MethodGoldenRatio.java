package lab1;

import java.util.function.Function;

public class MethodGoldenRatio extends Minimalizer {


    public MethodGoldenRatio(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double getMinimalValue(double epsilon) {
        return goldenRatio(leftBorder, rightBorder, epsilon);
    }


    private static double f(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
    }

    // Return x : f(x) = min
    public static Pair<Double, Double> goldenRatio(double a, double b, final double EPS) {
        // Step 1
        double x1 = a + (3 - Math.sqrt(5)) / 2 * (b - a);
        double x2 = a + (Math.sqrt(5) - 1) / 2 * (b - a);
        double f1 = f(x1);
        double f2 = f(x2);
        final double t = (Math.sqrt(5) - 1) / 2;
        double eps = (b - a) / 2;
        int i = 0;
        // Step 2
        while (eps > EPS) {
            // Step 3
            if (f1 - f2 <= EPS) {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = b - t * (b - a);
                f1 = f(x1);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = a + t * (b - a);
                f2 = f(x2);
            }
            eps *= t;
            i++;
        }
        // Step 4
        return new Pair<>(a, b);
    }


}
