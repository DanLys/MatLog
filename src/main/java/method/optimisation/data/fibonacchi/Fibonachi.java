package method.optimisation.data.fibonacchi;

import org.springframework.stereotype.Component;

@Component
public class Fibonachi {

    public void main(String[] args) {
        int n = 30;
        System.out.println(fibonacchiOptimisation(1, 5, 0.00001, n));
    }

    private double[] a, b, fibArr;
    private double x1, x2;
    private int k = 0, ind = 0;
    private final double DOUBLE_EPSILON = 0.000000001;

    private boolean doubleEquals(double x1, double x2) {
        return Math.abs(x2 - x1) < DOUBLE_EPSILON;
    }

    private double functionResult(double x) {
//        return Math.exp(x) - 2 * Math.pow(x, 2);
        return Math.sin(x) + 1 / x;
    }

    private void fillFibArr(final int n) {         // числа фибоначчи предпроцессинг
        fibArr = new double[n];
        fibArr[0] = 1;
        fibArr[1] = 1;
        long x1 = 1, x2 = 1, res;
        for (int i = 2; i < n; i++) {
            res = x1 + x2;
            x1 = x2;
            x2 = res;
            fibArr[i] = Double.parseDouble(Long.toString(res));
        }
    }

    private double fib(final int n) {        // get value fib
        return fibArr[n - 1];
    }

    private int minusOnePower(int power) {
        if (power % 2 == 0) {
            return 1;
        } else {
            return -1;
        }
    }


    public double fibonacchiOptimisation(double a1, double b1, double eps, int n) {    // main func
        fillFibArr(n);
        a = new double[n + 1];  // fix ind
        b = new double[n + 1];
        a[0] = a1;
        b[0] = b1;
        double l1 = b1 - a1;
        double l2 = fib(n - 1) / fib(n) * l1 + minusOnePower(n) * eps / fib(n);
        x2 = a1 + l2;
        stepOne(n);
        return (a[ind] + b[ind]) / 2;
    }

    private void stepOne(int n) {    // step 4
        x1 = a[ind] + (b[ind] - x2);
        double fX1 = functionResult(x1),
                fX2 = functionResult(x2);
        if (fX1 < fX2) {
            if (x1 < x2) {
                b[++ind] = x2;
                a[ind] = a[ind - 1];
            } else {    // x1 > x2
                a[++ind] = x2;
                b[ind] = b[ind - 1];
            }
            x2 = x1;
            fX2 = fX1;
        } else {
            if (x1 < x2) {
                a[++ind] = x1;
                b[ind] = b[ind - 1];
            } else {    // x1 > x2
                b[++ind] = x1;
                a[ind] = a[ind - 1];
            }
        }
        stepTwo(n);
    }

    private void stepTwo(int n) {
       k++;
       if (k < n) {
           stepOne(n);
       } // else finish
    }
}

