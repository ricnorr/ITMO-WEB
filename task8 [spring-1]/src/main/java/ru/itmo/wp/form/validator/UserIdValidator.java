package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;


@Component
public class UserIdValidator implements Validator {

    private final UserService userService;

    public UserIdValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return Long.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            User user =  (User) target;
            if (userService.findById(user.getId()) == null) {
                errors.rejectValue("id", "id.invalid-id", "invalid id");
            }
        }
    }
}
