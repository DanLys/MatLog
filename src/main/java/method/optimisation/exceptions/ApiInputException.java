package method.optimisation.exceptions;

/**
 * Created by Danil Lyskin at 16:50 28.02.2021
 */
public class ApiInputException extends RuntimeException {
    public ApiInputException(final String message) {
        super(message);
    }
}
