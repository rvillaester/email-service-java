package com.emailservice.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class ValidationResponse {

    private boolean valid = true;
    private List<String> validationMessages;

    public void addValidationMessage(String message) {
        if(validationMessages == null) validationMessages = new ArrayList<>();
        valid = false;
        validationMessages.add(message);
    }
}
