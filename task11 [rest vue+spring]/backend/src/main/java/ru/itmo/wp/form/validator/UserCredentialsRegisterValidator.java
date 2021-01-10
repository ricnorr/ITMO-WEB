package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.service.UserService;

import javax.annotation.Nonnull;

@Component
public class UserCredentialsRegisterValidator implements Validator {
    private final UserService userService;

    public UserCredentialsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(@Nonnull Class<?> clazz) {
        return UserRegisterCredentials.class.equals(clazz);
    }

    public void validate(@Nonnull Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserRegisterCredentials registerForm = (UserRegisterCredentials) target;
            if (userService.findByLogin(registerForm.getLogin()) != null) {
                errors.rejectValue("login", "login.invalid-login-or-password", "Login is occupied");
            }
        }
    }
}
