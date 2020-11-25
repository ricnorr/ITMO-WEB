package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisableUserCredentials;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.UserService;

@Component
public class DisableUserCredentialsValidator implements Validator {

    private UserService userService;

    public DisableUserCredentialsValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return DisableUserCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            DisableUserCredentials disableUserForm = (DisableUserCredentials) target;
            User user = userService.findById(disableUserForm.getId());
            if (user == null || !(disableUserForm.getAction().equals("enable") || disableUserForm.getAction().equals("disable"))) {
                errors.rejectValue("user.disable", "user.disable.form", "invalid user disable form");
            }
        }
    }
}
