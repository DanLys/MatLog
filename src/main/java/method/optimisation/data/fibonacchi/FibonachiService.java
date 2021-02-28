package method.optimisation.data.fibonacchi;

import method.optimisation.exceptions.ApiInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Danil Lyskin at 16:43 28.02.2021
 */

@Service
public class FibonachiService {

    private final MethodFibonachi methodFibonachi;

    @Autowired
    public FibonachiService(final MethodFibonachi methodFibonachi) {
        this.methodFibonachi = methodFibonachi;
    }

    public Double findResult(double a, double b, int iterations) {
        if (a > b) {
            throw new ApiInputException(String.format("First element is bigger then second: %f %f", a, b));
        }
        return methodFibonachi.fibonacchiOptimisation(a, b, iterations);
    }
}
