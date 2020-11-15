package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.form.validator.UserIdValidator;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserPage extends Page {
    private final UserService userService;
    //private final UserIdValidator userIdValidator;

    public UserPage(UserService userService, UserIdValidator userIdValidator) {
        this.userService = userService;
        //this.userIdValidator = userIdValidator;
    }

    /* @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userIdValidator);
    } */


    @GetMapping({"/user/{userId}"})
    public String user(@Valid @ModelAttribute("user") @PathVariable("userId") User user, HttpSession httpSession) {
        /* if (bindingResult.hasErrors()) {
            return "UserPage";
        } */
        setUser(httpSession, user);
        return "UserPage";
    }
}
