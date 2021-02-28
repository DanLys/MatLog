package method.optimisation.base_control;

import method.optimisation.attributes.MethodsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Danil Lyskin at 15:02 25.02.2021
 */

@RestController
public class BaseController {

    private final BaseService baseService;

    @Autowired
    public BaseController(final BaseService baseService) {
        this.baseService = baseService;
    }

    @RequestMapping(value = "api/get/result/{a}/{b}/{iterator}", method = RequestMethod.GET)
    public ResponseEntity<String> getAnswer(@PathVariable("a") final Double a,
                                            @PathVariable("b") final Double b,
                                            @PathVariable("iterator") final Integer iterator) {
        return ResponseEntity.ok(String.valueOf(baseService.solve(a, b, iterator)));
    }

    @RequestMapping(value = "api/change/method/{method}", method = RequestMethod.POST)
    public ResponseEntity<String> changeMethod(@PathVariable("method") final String method) {
        baseService.setType(MethodsType.of(method));
        return ResponseEntity.ok("{}");
    }

}
