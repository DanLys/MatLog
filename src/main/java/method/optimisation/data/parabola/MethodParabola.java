package method.optimisation.data.parabola;

import org.springframework.stereotype.Component;

@Component
public class MethodParabola {
    public void main(String[] args) {
        System.out.println(launchParabolaMethod(4, 7));
    }

    private final Double EPS = 1e-7;

    private static class TripleF {
        public static double fX1, fX2, fX3;    // значения функции в точках x1, x2, x3
    }

    private class TripleX {
        public double x1, x2, x3;

        private TripleX(double x1, double x2, double x3) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
        }
    }

    private double functionResult(double x) {
        return 0.2 * x * Math.log(x) + Math.pow((x - 2.3), 2);
//        return Math.sin(x) + 1 / x; // 4.7566019161693704
    }

    public double launchParabolaMethod(double a, double b) {
        return parabolaMethod(new TripleX(a, chooseX2(a, b), b));
    }

    private double parabolaMethod(TripleX tripleX) {  // main parabola
        double prevXmin = stepOne(tripleX);
        TripleX tripleX1 = stepThree(tripleX, prevXmin);
        double xMin;
        while (true) {
            xMin = stepOne(tripleX1);
            if (stepTwo(prevXmin, xMin, EPS)) {
                break;
            }
            prevXmin = xMin;
            stepThree(tripleX1, xMin);
        }
        return xMin;
    }

    private double chooseX2(double a, double b) {  // методом золотого сечения
        double x21 = a + (3 - Math.sqrt(5)) / 2 * (b - a),
                x22 = a + (Math.sqrt(5) - 1) / 2 * (b - a),
                fB = functionResult(b),
                fX21 = functionResult(x21),
                fX22 = functionResult(x22);
        if (fX21 <= fB) return x21;
        if (fX22 <= fB) return x22;
        return chooseX2(x22, b);
    }

    private double stepOne(TripleX tripleX) {
        TripleF.fX1 = functionResult(tripleX.x1);
        TripleF.fX2 = functionResult(tripleX.x2);
        TripleF.fX3 = functionResult(tripleX.x3);
        double a1 = (TripleF.fX2 - TripleF.fX1) / (tripleX.x2 - tripleX.x1),
                a2 = 1 / (tripleX.x3 - tripleX.x2) * ((TripleF.fX3 - TripleF.fX1) / (tripleX.x3 - tripleX.x1)
                 - (TripleF.fX2 - TripleF.fX1) / (tripleX.x2 - tripleX.x1));
        return 0.5 * (tripleX.x1 + tripleX.x2 - a1 / a2);
    }

    private boolean stepTwo(double prevMin, double xMin, double eps) {
        return Math.abs(prevMin - xMin) <= eps;
    }

    private TripleX stepThree(TripleX tripleX, double xMin) {  // define new x1, x2, x3
        double fXmin = functionResult(xMin);
        if (tripleX.x1 < xMin
                && xMin < tripleX.x2
                && tripleX.x2 < tripleX.x3) {
            if (fXmin >= TripleF.fX2) {
                tripleX.x1 = xMin;
                TripleF.fX1 = fXmin;
            } else {
                tripleX.x3 = tripleX.x2;
                tripleX.x2 = xMin;
                TripleF.fX3 = TripleF.fX2;
                TripleF.fX2 = fXmin;
            }
        }

        if (tripleX.x1 < tripleX.x2
                && tripleX.x2 < xMin
                && xMin < tripleX.x3) {
            if (TripleF.fX2 >= fXmin) {
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

