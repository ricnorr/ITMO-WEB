package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.WritePostForm;

@Component
public class WritePostFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return (WritePostForm.class.equals(aClass));
    }

    @Override
    public void validate(Object o, Errors errors) {
          WritePostForm writePostForm = (WritePostForm) o;
          if (writePostForm.getText() == null || writePostForm.getText().length() > 65000 || writePostForm.getText().isEmpty()) {
              errors.rejectValue("text", "text.error", "Text should have 1...65000 characters");
              return;
          }

        if (writePostForm.getTitle() == null || writePostForm.getTitle().length() > 255 || writePostForm.getTitle().isEmpty()) {
            errors.rejectValue("title", "title.error", "Title should have 1...65000 characters");
            return;
        }

        if (writePostForm.getTags() == null || writePostForm.getTags().length() > 255 || writePostForm.getTags().isEmpty()) {
            errors.rejectValue("tags", "tags.error", "Tags should have 0...255 characters in sum");
            return;
        }

        String[] tags = writePostForm.getTags().split("\\s+");
        for (String string : tags) {
            if (!string.matches("^[a-zA-Z.]+$")) {
                errors.rejectValue("tags", "tags.error", "Tags should have only latin letters");
            }
        }
    }
}
