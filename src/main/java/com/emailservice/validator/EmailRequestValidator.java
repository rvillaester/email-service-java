package com.emailservice.validator;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.ValidationResponse;
import com.emailservice.util.ApplicationUtil;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EmailRequestValidator {

    public ValidationResponse validate(EmailRequest emailRequest) {
        ValidationResponse response = new ValidationResponse();
        if(ApplicationUtil.isEmpty(emailRequest.getSubject())) response.addValidationMessage("Subject is required");
        if(ApplicationUtil.isEmpty(emailRequest.getMessage())) response.addValidationMessage("Message is required");

        String[] recipients = emailRequest.getRecipients();
        String[] ccRecipients = emailRequest.getCcRecipients();
        String[] bccRecipients = emailRequest.getBccRecipients();
        if(ApplicationUtil.isEmpty(recipients)) response.addValidationMessage("To is required");
        if(!isValidEmails(recipients)) response.addValidationMessage("To contains invalid emails");
        if(!isValidEmails(ccRecipients)) response.addValidationMessage("CC contains invalid emails");
        if(!isValidEmails(bccRecipients)) response.addValidationMessage("BCC contains invalid emails");
        return response;
    }

    private boolean isValidEmails(String[] emails) {
        if(ApplicationUtil.isEmpty(emails)) return true;
        return !Arrays.stream(emails).anyMatch(email -> !EmailValidator.getInstance().isValid(email));
    }
}
