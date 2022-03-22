package com.emailservice.controller;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.EmailResponse;
import com.emailservice.model.ValidationResponse;
import com.emailservice.provider.EmailProvider;
import com.emailservice.validator.EmailRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private List<EmailProvider> providers;

    @Autowired
    private EmailRequestValidator validator;

    @PostMapping("/send")
    public EmailResponse send(@RequestBody EmailRequest emailRequest) {
        System.out.println(String.format("Request received: %s", emailRequest));
        EmailResponse response = validateRequest(emailRequest);
        if(response != null) return response;
        return processRequest(emailRequest);
    }

    private EmailResponse processRequest(EmailRequest emailRequest) {
        EmailResponse response = null;
        for (EmailProvider provider : providers) {
            String name = provider.getName();
            System.out.println(String.format("Trying to send email via %s", name));
            response = provider.process(emailRequest);
            if(response.isSuccess()){
                System.out.println(String.format("Email sent via %s", name));
                break;
            }
        }
        return response;
    }
    private EmailResponse validateRequest(EmailRequest emailRequest) {
        ValidationResponse validationResponse = validator.validate(emailRequest);
        if(!validationResponse.isValid()) {
            EmailResponse response = EmailResponse.validationError();
            response.setValidationMessages(validationResponse.getValidationMessages());
            System.out.println(String.format("Validation error: %s", validationResponse));
            return response;
        }
        return null;
    }
}
