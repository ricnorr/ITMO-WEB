package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final NoticeService userService;

    public UsersPage(NoticeService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model, HttpSession session) {
        getAllNotices(session);
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }
}
