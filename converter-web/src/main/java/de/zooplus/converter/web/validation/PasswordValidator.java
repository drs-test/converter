package de.zooplus.converter.web.validation;

import de.zooplus.converter.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by dragan
 */
@Component
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (errors.getFieldErrors("repeatedPassword").isEmpty()) {
            if (!(user.getPassword().equals(user.getRepeatedPassword()))) {
                errors.rejectValue("repeatedPassword", "javax.validation.constraints.PasswordNotMatch.message");
            }
        }
    }
}
