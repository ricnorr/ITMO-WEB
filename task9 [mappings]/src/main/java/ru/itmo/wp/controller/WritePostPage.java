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
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.WritePostForm;
import ru.itmo.wp.form.validator.WritePostFormValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final WritePostFormValidator writePostFormValidator;
    private final List<Validator> validatorList;

    public WritePostPage(UserService userService, WritePostFormValidator writePostFormValidator) {
        this.userService = userService;
        this.writePostFormValidator = writePostFormValidator;
        validatorList = List.of(writePostFormValidator);
    }

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        for (Validator validator : validatorList) {
            if (validator.supports(webDataBinder.getTarget().getClass())) {
                webDataBinder.addValidators(validator);
            }
        }
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("writePost", new Post());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("writePost") WritePostForm postForm,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }

        userService.writePost(getUser(httpSession), postForm);
        putMessage(httpSession, "You published new post");

        return "redirect:/posts";
    }
}
