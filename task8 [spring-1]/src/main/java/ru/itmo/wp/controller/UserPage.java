package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Controller
public class UserPage extends Page {
    private final UserService userService;
    private final String USER_PAGE_ERROR_URL = "/user/error";

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/user/error"})
    public String error(Model model) {
        model.addAttribute("error", "No such user");
        return "UserPage";
    }

    @GetMapping({"/user/{userId}"})
    public String user(Model model, @PathVariable("userId") String userIdString, HttpSession session) {
        getAllNotices(session);
        Long userId;
        if (userIdString == null) {
            return "redirect:" + USER_PAGE_ERROR_URL;
        }
        try {
            userId = Long.valueOf(userIdString);
        } catch (NumberFormatException e) {
            return "redirect:" + USER_PAGE_ERROR_URL;
        }
        User user = userService.findById(userId);
        if (user == null) {
            return "redirect:" + USER_PAGE_ERROR_URL;
        }
        model.addAttribute("userInfo", userService.findById(userId));
        return "UserPage";
    }

    @GetMapping({"/user/"})
    public String user(Model model) {
        return "redirect:" + USER_PAGE_ERROR_URL;
    }
}
