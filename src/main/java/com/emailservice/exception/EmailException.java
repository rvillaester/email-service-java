package com.emailservice.exception;

import org.springframework.util.StringUtils;

public class EmailException extends RuntimeException {

    private static final String PREFIX = "Email Exception: ";
    private String errorMessage;

    public EmailException(Exception ex) {
        super(ex);
        this.errorMessage = ex.getMessage();
    }

    @Override
    public String toString() {
        if(StringUtils.hasText(errorMessage)) return String.format("%s%s", PREFIX, getMessage());
        return String.format("%s%s", PREFIX, errorMessage);
    }
}
