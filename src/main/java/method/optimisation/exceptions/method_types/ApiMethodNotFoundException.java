package method.optimisation.exceptions.method_types;

/**
 * Created by Danil Lyskin at 16:25 25.02.2021
 */
public class ApiMethodNotFoundException extends RuntimeException {
    public ApiMethodNotFoundException(final String message) {
        super(message);
    }
}
