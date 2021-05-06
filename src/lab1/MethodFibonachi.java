package lab1;


import java.util.function.Function;

public class MethodFibonachi extends Minimalizer {

    private static double[] a, b, fibArr;
    private static double x1, x2, fX1, fX2;
    private static int k = 0, ind = 0;
    private static final int MAX_N = 120;

    public MethodFibonachi(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double getMinimalValue(double epsilon) {
        int n = chooseN(epsilon);
        fillFibArr(n);
        return fibonacchiOptimisation(n, epsilon);
    }

    private int chooseN(final double eps) {
        for (int i = 1; i < MAX_N; i++) {
            if ((rightBorder - leftBorder) / eps < fib(i)) {
                return i;
            }
        }
        return MAX_N;
    }



    private static double functionResult(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
    }

    private static void fillFibArr(final int n) {         // числа фибоначчи предпроцессинг
        fibArr = new double[n];
        fibArr[0] = 1d;
        fibArr[1] = 1d;
        long x1 = 1, x2 = 1, res;
        for (int i = 2; i < n; i++) {
            res = x1 + x2;
            x1 = x2;
            x2 = res;
            fibArr[i] = Double.parseDouble(Long.toString(res));
        }
    }

    private static double fib(final int n) {        // get value fib
        return fibArr[n - 1];
    }

    private static int minusOnePower(int power) {
        if (power % 2 == 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public Pair<Double, Double> fibonacchiOptimisation(int n, final double EPS) {    // main func
//        fillFibArr(MAX_N);
        a = new double[n + 1];
        b = new double[n + 1];
        a[0] = leftBorder;
        b[0] = rightBorder;
        ind = 0;
        k = 0;
        double l1 = rightBorder - leftBorder;
        double l2 = fib(n - 1) / fib(n) * l1 + minusOnePower(n) * EPS / fib(n);
        x2 = leftBorder + l2;
        fX2 = functionResult(x2);
        stepOne(n, EPS);
        return new Pair<>(a[ind], b[ind]);
    }

    private void stepOne(int n, final double EPS) { // step 4
        x1 = a[ind] + (b[ind] - x2);
        double fX1 = functionResult(x1);
        if (isLess(fX1, fX2, EPS)) {
            if (isLess(x1, x2, EPS)) {
                b[++ind] = x2;
                a[ind] = a[ind - 1];
            } else {    // x1 > x2
                a[++ind] = x2;
                b[ind] = b[ind - 1];
            }
            x2 = x1;
            fX2 = fX1;
        } else {
            if (isLess(x1, x2, EPS)) {
                a[++ind] = x1;
                b[ind] = b[ind - 1];
            } else {    // x1 > x2
                b[++ind] = x1;
                a[ind] = a[ind - 1];
            }
        }
        stepTwo(n, EPS);
    }

    private void stepTwo(int n, final double EPS) {
        k++;
        if (k < n) {
            stepOne(n, EPS);
        } // else finish
    }


}



