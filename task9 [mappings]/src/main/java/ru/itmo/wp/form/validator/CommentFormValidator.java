package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.service.PostService;


@Component
public class CommentFormValidator implements Validator {
    private final PostService postService;

    public CommentFormValidator(PostService postService) {
        this.postService = postService;
    }
    public boolean supports(Class<?> clazz) {
        return CommentForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            CommentForm commentForm = (CommentForm) target;
            if (commentForm.getText() == null || commentForm.getText().length() == 0 || commentForm.getText().length() > 255) {
                errors.rejectValue("text", "text.error", "invalid comment");
            }
        }
    }
}
