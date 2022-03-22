package com.emailservice.validator;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.ValidationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailRequestValidatorTest {

    private EmailRequestValidator validator = new EmailRequestValidator();

    private EmailRequest request;

    @BeforeEach
    public void setup() {
        request = new EmailRequest();
        request.setSubject("subject");
        request.setMessage("message");
        request.setRecipients(new String[] {"rey@test.com"});
        request.setCcRecipients(new String[] {"rey@test.com"});
        request.setBccRecipients(new String[] {"rey@test.com"});
    }

    @Test
    public void canValidateEmptySubject() {
        request.setSubject("");
        ValidationResponse response = validator.validate(request);
        assertAll("Missing subject scenario",
                () -> assertFalse(response.isValid()),
                () -> assertEquals(response.getValidationMessages().size(), 1),
                () -> assertEquals(response.getValidationMessages().get(0), "Subject is required"));
    }

    @Test
    public void canValidateEmptyMessage() {
        request.setMessage(null);
        ValidationResponse response = validator.validate(request);
        assertAll("Missing message scenario",
                () -> assertFalse(response.isValid()),
                () -> assertEquals(response.getValidationMessages().size(), 1),
                () -> assertEquals(response.getValidationMessages().get(0), "Message is required"));
    }

    @Test
    public void canValidateEmptyRecipients() {
        request.setRecipients(null);
        ValidationResponse response = validator.validate(request);
        assertAll("Missing recipients scenario",
                () -> assertFalse(response.isValid()),
                () -> assertEquals(response.getValidationMessages().size(), 1),
                () -> assertEquals(response.getValidationMessages().get(0), "To is required"));
    }

    @Test
    public void canValidateInvalidRecipients() {
        request.setRecipients(new String[] {"rey@test.com", "xxxxx", "f@gmail.com"});
        request.setCcRecipients(new String[] {"invalid cc", "cc@test.com", "f@gmail.com"});
        request.setBccRecipients(new String[] {"bcc@test.com", "f@gmail.com", "invalid bcc"});
        ValidationResponse response = validator.validate(request);
        assertAll("Invalid recipients scenario",
                () -> assertFalse(response.isValid()),
                () -> assertEquals(response.getValidationMessages().size(), 3),
                () -> assertEquals(response.getValidationMessages().get(0), "To contains invalid emails"),
                () -> assertEquals(response.getValidationMessages().get(1), "CC contains invalid emails"),
                () -> assertEquals(response.getValidationMessages().get(2), "BCC contains invalid emails"));
    }
}
