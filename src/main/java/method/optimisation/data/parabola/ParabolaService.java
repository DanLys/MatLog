package method.optimisation.data.parabola;

import method.optimisation.exceptions.ApiInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Danil Lyskin at 17:13 28.02.2021
 */

@Service
public class ParabolaService {

    private final MethodParabola methodParabola;

    @Autowired
    public ParabolaService(final MethodParabola methodParabola) {
        this.methodParabola = methodParabola;
    }

    public Double findResult(double a, double b, int iterations) {
        if (a > b) {
            throw new ApiInputException(String.format("First element is bigger then second: %f %f", a, b));
        }
        return methodParabola.launchParabolaMethod(a, b);
    }
}
