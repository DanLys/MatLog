package method.optimisation.base_control;

import lombok.Setter;
import method.optimisation.attributes.MethodsType;
import method.optimisation.data.dichotomy.DichotomyService;
import method.optimisation.data.fibonacchi.FibonachiService;
import method.optimisation.exceptions.method_types.ApiMethodNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Danil Lyskin at 16:15 25.02.2021
 */

@Service
@Transactional
@Setter
public class BaseService {
    
    private MethodsType type;

    private final DichotomyService dichotomyService;
    private final FibonachiService fibonachiService;


    public BaseService(final DichotomyService dichotomyService,
                       final FibonachiService fibonachiService) {
        this.fibonachiService = fibonachiService;
        type = MethodsType.FIBONACHI;
        this.dichotomyService = dichotomyService;
    }

    public Double solve(double a, double b, int iterator) {
        switch (type) {
            case DICHOTOMY: return dichotomyService.findResult();
            case FIBONACHI: return fibonachiService.findResult(a, b, iterator);
        }

        throw new ApiMethodNotFoundException(String.format("Method %s does not exist", type.getType()));
    }

}
