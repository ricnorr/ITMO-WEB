package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.validator.CommentFormValidator;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PostPage extends Page {
    private final String POST_ERROR_REDIRECT_URL = "/post/error";
    private final PostService postService;
    private final UserService userService;
    private final List<Validator> validatorList;
    //private final CommentCredentialsValidator commentCredentialsValidator;

    public PostPage(PostService postService, UserService userService, CommentFormValidator commentFormValidator) {
        this.postService = postService;
        this.userService = userService;
        this.validatorList = List.of(commentFormValidator);
    }


    @GetMapping("/post/error")
    public String error(Model model) {
        model.addAttribute("error", "Post not found");
        return "PostPage";
    }

    @GetMapping("/post/{stringId}")
    @Guest
    public String post(@PathVariable String stringId, Model model) {
        long id;
        try {
            id = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            return "redirect:" + POST_ERROR_REDIRECT_URL;
        }
        if (postService.find(id) == null) {
            return "redirect:" + POST_ERROR_REDIRECT_URL;
        }
        model.addAttribute("post", postService.find(id));
        model.addAttribute("commentForm", new CommentForm());
        return "PostPage";
    }


    @PostMapping("/post/{stringId}")
    public String postPost(@PathVariable String stringId, @Valid @ModelAttribute(name = "commentForm") CommentForm commentForm, BindingResult bindingResult, Model model,
                           HttpSession session, Errors errors) {
        long id;
        try {
            id = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            return "redirect:" + POST_ERROR_REDIRECT_URL;
        }
        if (postService.find(id) == null) {
            return "redirect:" + POST_ERROR_REDIRECT_URL;
        }
        model.addAttribute("post", postService.find(id));
        if (errors.hasErrors()) {
            return "PostPage";
        }
         postService.writeComment(getUser(session), commentForm, id);
         return "PostPage";
    }

    @PostMapping("/post/")
    public String postPost() {
        return "redirect:" + POST_ERROR_REDIRECT_URL;
    }

    @GetMapping("/post/")
    public String post() {
        return "redirect:" + POST_ERROR_REDIRECT_URL;
    }




}
