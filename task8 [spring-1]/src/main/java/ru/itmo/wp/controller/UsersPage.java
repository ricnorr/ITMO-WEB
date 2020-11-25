package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.DisableUserCredentials;
import ru.itmo.wp.form.validator.DisableUserCredentialsValidator;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersPage extends Page {
    private final UserService userService;
    private final DisableUserCredentialsValidator disableUserCredentialsValidator;
    private final List<Validator> validatorsList;
    private final String USERS_ALL_URL = "/users/all";

    public UsersPage(UserService userService, DisableUserCredentialsValidator disableUserCredentialsValidator) {
        this.disableUserCredentialsValidator = disableUserCredentialsValidator;
        validatorsList = List.of(disableUserCredentialsValidator);
        this.userService = userService;

    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        for (Validator validator : validatorsList) {
            if (validator.supports(webDataBinder.getTarget().getClass())) {
                webDataBinder.addValidators(validator);
            }
        }
    }

    @GetMapping("/users/all")
    public String usersGet(Model model, HttpSession session) {
        getAllNotices(session);
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String usersPost(@Valid @ModelAttribute("disableUser.form") DisableUserCredentials disableUserCredentials, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:" + USERS_ALL_URL;
        }
        if (disableUserCredentials.getAction().equals("disable")) {
            userService.setDisabledTrue(disableUserCredentials.getId());
        } else {
            userService.setDisabledFalse(disableUserCredentials.getId());
        }
        return "redirect:" + USERS_ALL_URL;
    }
}
