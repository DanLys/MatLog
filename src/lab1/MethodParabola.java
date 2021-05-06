package lab1;

import java.util.function.Function;

public class MethodParabola extends Minimalizer {


    public MethodParabola(Function<Double, Double> function, double leftBorder, double rightBorder) {
        super(function, leftBorder, rightBorder);
    }

    @Override
    public double getMinimalValue(double epsilon) {
        return launchParabolaMethod(leftBorder, rightBorder, epsilon);
    }


    private static class TripleF {
        public static double fX1, fX2, fX3;    // значения функции в точках x1, x2, x3
    }

    private static class TripleX {
        public double x1, x2, x3;

        private TripleX(double x1, double x2, double x3) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
        }
    }

    private double functionResult(double x) {
        return 0.2 * x * Math.log10(x) + Math.pow((x - 2.3), 2);
    }

    public Pair<Double, Pair<Double, Double>> launchParabolaMethod(double a, double b, final double EPS) {
        return parabolaMethod(new TripleX(a, chooseX2(a, b, EPS), b), EPS);
    }

    private Pair<Double, Pair<Double, Double>> parabolaMethod(TripleX tripleX, final double EPS) {  // main parabola
        TripleF.fX1 = functionResult(tripleX.x1);
        TripleF.fX2 = functionResult(tripleX.x2);
        TripleF.fX3 = functionResult(tripleX.x3);
        double prevXmin = stepOne(tripleX);
        TripleX tripleX1 = stepThree(tripleX, prevXmin, EPS);
        double xMin = 0d, temp;
        while (true) {
            temp = stepOne(tripleX1);
            if (temp == Double.MIN_VALUE) {
                break;
            }
            xMin = temp;
            prevXmin = xMin;
            tripleX1 = stepThree(tripleX1, xMin, EPS);
        }
        return new Pair<>(tripleX1.x1, new Pair<>(tripleX1.x2, tripleX1.x3));
    }

    private double chooseX2(double a, double b, final double EPS) {  // методом золотого сечения
        double x21 = a + (3 - Math.sqrt(5)) / 2 * (b - a),
                x22 = a + (Math.sqrt(5) - 1) / 2 * (b - a),
                fB = functionResult(b),
                fX21 = functionResult(x21),
                fX22 = functionResult(x22);
        if (isLessOrEqual(fX21, fB, EPS)) return x21;
        if (isLessOrEqual(fX22, fB, EPS)) return x22;
        return chooseX2(x22, b, EPS);
    }

    private  double stepOne(TripleX tripleX) {
        if (tripleX.x1 == tripleX.x2 || tripleX.x1 == tripleX.x3 || tripleX.x2 == tripleX.x3) {
            return Double.MIN_VALUE;
        }
        double a1 = (TripleF.fX2 - TripleF.fX1) / (tripleX.x2 - tripleX.x1),
                a2 = 1 / (tripleX.x3 - tripleX.x2) * ((TripleF.fX3 - TripleF.fX1) / (tripleX.x3 - tripleX.x1)
                        - (TripleF.fX2 - TripleF.fX1) / (tripleX.x2 - tripleX.x1));
        return 0.5 * (tripleX.x1 + tripleX.x2 - a1 / a2);
    }

    //    private boolean stepTwo(double prevMin, double xMin) {
//        return Math.abs(prevMin - xMin) <= EPS;
//    }

    private  TripleX stepThree(TripleX tripleX, double xMin, final double EPS) {  // define new x1, x2, x3
        double fXmin = functionResult(xMin);
        if (isLess(tripleX.x1, xMin, EPS)
                && isLess(xMin, tripleX.x2, EPS)
                && isLess(tripleX.x2, tripleX.x3, EPS)) {
            if (isBiggerOrEqual(fXmin, TripleF.fX2, EPS)) {
                tripleX.x1 = xMin;
                TripleF.fX1 = fXmin;
            } else {
                tripleX.x3 = tripleX.x2;
                tripleX.x2 = xMin;
                TripleF.fX3 = TripleF.fX2;
                TripleF.fX2 = fXmin;
            }
        }

        if (isLess(tripleX.x1, tripleX.x2, EPS)
                && isLess(tripleX.x2, xMin, EPS)
                && isLess(xMin, tripleX.x3, EPS)) {
            if (isBiggerOrEqual(TripleF.fX2, fXmin, EPS)) {
                tripleX.x1 = tripleX.x2;
                TripleF.fX1 = TripleF.fX2;
                tripleX.x2 = xMin;
                TripleF.fX2 = fXmin;
            } else {
                tripleX.x3 = xMin;
                TripleF.fX3 = fXmin;
            }
        }
        return tripleX;
    }
}





