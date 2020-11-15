package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsValidator;
import ru.itmo.wp.service.NoticeService;

import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeCredentialsValidator noticeCredentialsValidator;

    public NoticePage(NoticeService noticeService, NoticeCredentialsValidator noticeCredentialsValidator) {
        this.noticeCredentialsValidator = noticeCredentialsValidator;
        this.noticeService = noticeService;
    }

    @InitBinder
    public void initBind(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(noticeCredentialsValidator);
    }

    @GetMapping("/notice")
    public String notice(Model model) {
        getNotices();
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String notice(@Valid @ModelAttribute("noticeForm") NoticeCredentials notice, Errors errors) {
        if (errors.hasErrors()) {
            return "NoticePage";
        }
        noticeService.save(notice);
        return "redirect:";
    }
}
