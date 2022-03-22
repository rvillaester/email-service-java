package com.emailservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
@Builder
public class EmailResponse {
    private String id;
    private Status status;
    private String message;
    private List<String> validationMessages;

    public static EmailResponse internalError() {
        return EmailResponse.builder().status(Status.FAILED).message("Internal Error").build();
    }

    public static EmailResponse validationError() {
        return EmailResponse.builder().status(Status.VALIDATION_ERROR).message("Validation Error").build();
    }

    public static EmailResponse sendFailed(String message) {
        return EmailResponse.builder().status(Status.FAILED).message(message).build();
    }

    public static EmailResponse sendFailed() {
        return EmailResponse.builder().status(Status.FAILED).message("Failed in sending email").build();
    }

    @JsonIgnore
    public boolean isSuccess(){
        return Status.SUCCESS.equals(status);
    }
}