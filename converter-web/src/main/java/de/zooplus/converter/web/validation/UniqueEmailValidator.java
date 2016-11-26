package de.zooplus.converter.web.validation;

import de.zooplus.converter.model.entity.User;
import de.zooplus.converter.service.internal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by dragan
 */
@Component
public class UniqueEmailValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (errors.getFieldErrors("email").isEmpty()) {
            if (!userService.checkEmailUnique(user.getEmail())) {
                errors.rejectValue("email", "javax.validation.constraints.NotUniqueEmailAddress.message");
            }
        }
    }
}
