package lab4;

import lab4.function.*;
import lab4.function.test.*;
import lab4.marquardt.MarquardtWithCholeskySolver;
import lab4.marquardt.MarquardtWithGaussSolver;
import lab4.newton.*;
import lab4.quasi.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Function f = new Function3_4();
        double[] x0 = new double[]{10, 10};
        double epsilon = 1e-5;
//        NewtonClassic m1 = new NewtonClassic(epsilon);
//        NewtonWithOneDimensionSearch m2 = new NewtonWithOneDimensionSearch(epsilon);
//        NewtonWithDescentDirection m3 = new NewtonWithDescentDirection(epsilon);
//        m1.findMin(f, x0);
//        m2.findMin(f, x0);
//        m3.findMin(f, x0);
//        FPDMethod m1 = new FPDMethod(epsilon);
//        PowellMethod m2 = new PowellMethod(epsilon);
//        NewtonWithOneDimensionSearch m3 = new NewtonWithOneDimensionSearch(epsilon);
//        m1.findMin(f, x0);
//        m2.findMin(f, x0);
//        m3.findMin(f, x0);
    }
}
