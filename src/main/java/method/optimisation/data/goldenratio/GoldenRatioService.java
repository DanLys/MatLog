package method.optimisation.data.goldenratio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Danil Lyskin at 21:59 09.03.2021
 */

@Service
public class GoldenRatioService {

    private final MethodGoldenRatio methodGoldenRatio;

    @Autowired
    public GoldenRatioService(final MethodGoldenRatio methodGoldenRatio) {
        this.methodGoldenRatio = methodGoldenRatio;
    }

    public Double findResult(double a, double b, int iterations) {
        return methodGoldenRatio.goldenRatio(a, b, iterations);
    }
}
