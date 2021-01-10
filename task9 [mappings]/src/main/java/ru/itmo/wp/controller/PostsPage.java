package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.service.PostService;

@Controller
public class PostsPage extends Page {
    private final PostService postService;

    public PostsPage(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String posts() {
        return "PostsPage";
    }

}
