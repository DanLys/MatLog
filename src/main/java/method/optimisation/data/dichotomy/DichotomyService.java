package method.optimisation.data.dichotomy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Danil Lyskin at 15:08 25.02.2021
 */

@Service
@Transactional
public class DichotomyService {

    private final MethodDichotomy methodDichotomy;

    @Autowired
    public DichotomyService(final MethodDichotomy methodDichotomy) {
        this.methodDichotomy = methodDichotomy;
    }

    public Double findResult() {
        return methodDichotomy.solve();
    }
}
