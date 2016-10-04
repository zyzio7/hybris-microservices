package pl.zyskowski.hybris.access.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import pl.zyskowski.hybris.access.AuthenticationRepository;
import pl.zyskowski.hybris.access.annotation.UserExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by zyzio on 04.10.2016.
 */
public class UserExistValidator implements ConstraintValidator<UserExist, String> {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public void initialize(UserExist userExist) {

    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
        try {
            User user = authenticationRepository.getUser(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
