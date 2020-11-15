package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeCredentials;



@Component
public class NoticeCredentialsValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeCredentials noticeForm = (NoticeCredentials) target;
            if (noticeForm.getContent() == null || noticeForm.getContent().length() < 1 || noticeForm.getContent().length() > 255) {
                errors.rejectValue("notice", "notice.invalid-content", "invalid content");
            }
        }
    }
}
