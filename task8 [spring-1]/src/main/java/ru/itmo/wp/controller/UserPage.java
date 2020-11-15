package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;

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
    public String user(Model model, @PathVariable("userId") Long userId, HttpSession session) {
        getAllNotices(session);
        if (userId == null) {
            return "redirect:" + USER_PAGE_ERROR_URL;
        }
        User user = userService.findById(userId);
        if (user == null) {
            return "redirect:" + USER_PAGE_ERROR_URL;
        } else {
            model.addAttribute("user", userService.findById(userId));
            return "UserPage";
        }
    }
}
