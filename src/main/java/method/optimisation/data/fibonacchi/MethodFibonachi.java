package method.optimisation.data.fibonacchi;

import org.springframework.stereotype.Component;

@Component
public class MethodFibonachi extends method.optimisation.data.AbstractComparator {

    private double[] a, b, fibArr;
    private double x1, x2;
    private int k = 0, ind = 0;

    private double functionResult(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
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

    public double fibonacchiOptimisation(double a1, double b1, int n) {    // main func
        fillFibArr(n);
        a = new double[n + 1];  // fix ind
        b = new double[n + 1];
        a[0] = a1;
        b[0] = b1;
        ind = 0;
        k = 0;
        double l1 = b1 - a1;
        double l2 = fib(n - 1) / fib(n) * l1 + minusOnePower(n) * EPS / fib(n);
        x2 = a1 + l2;
        stepOne(n);
        return (a[ind] + b[ind]) / 2;
    }

    private void stepOne(int n) { // step 4
        x1 = a[ind] + (b[ind] - x2);
        double fX1 = functionResult(x1),
                fX2 = functionResult(x2);
        if (isLess(fX1, fX2)) {
            if (isLess(x1, x2)) {
                b[++ind] = x2;
                a[ind] = a[ind - 1];
            } else {    // x1 > x2
                a[++ind] = x2;
                b[ind] = b[ind - 1];
            }
            x2 = x1;
        } else {
            if (isLess(x1, x2)) {
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



