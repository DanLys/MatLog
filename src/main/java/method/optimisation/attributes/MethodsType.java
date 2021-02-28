package method.optimisation.attributes;

import method.optimisation.exceptions.method_types.ApiMethodTypeException;

/**
 * Created by Danil Lyskin at 16:04 25.02.2021
 */
public enum MethodsType {
    DICHOTOMY(0L, "Dichotomy"),
    FIBONACHI(1L, "Fibonachi");

    MethodsType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public static MethodsType of(String str) {
        for (MethodsType value : values()) {
            if ((value.getType().toLowerCase()).equals(str.toLowerCase())) {
                return value;
            }
        }
        throw new ApiMethodTypeException("Method type: " + str + " doesn`t exist");
    }

    private final Long id;
    private final String type;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

}
