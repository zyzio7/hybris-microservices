package pl.zyskowski.hybris.access.annotation;

import pl.zyskowski.hybris.access.validator.UserExistValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = { UserExistValidator.class }
)
public @interface UserExist {

    String message() default "Given token is not bind to user!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
