package method.optimisation.data.brent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Danil Lyskin at 21:55 09.03.2021
 */
@Service
public class CombinationOfBrentService {

    private final MethodCombinationOfBrent methodCombinationOfBrent;

    @Autowired
    public CombinationOfBrentService(final MethodCombinationOfBrent methodCombinationOfBrent) {
        this.methodCombinationOfBrent = methodCombinationOfBrent;
    }

    public Double findResult(double a, double b, int iterations) {
        return methodCombinationOfBrent.combinationOfBrent(a, b, iterations);
    }
}
