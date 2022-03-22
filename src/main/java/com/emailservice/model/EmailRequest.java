package com.emailservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.stream.Collectors;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class EmailRequest {

    @JsonProperty("to")
    private String[] recipients;

    @JsonProperty("cc")
    private String[] ccRecipients;

    @JsonProperty("bcc")
    private String[] bccRecipients;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    public String getFormattedCCRecipients() {
        return getFormattedRecipients(ccRecipients);
    }

    public String getFormattedBCCRecipients() {
        return getFormattedRecipients(bccRecipients);
    }

    public String getFormattedRecipients() {
        return getFormattedRecipients(recipients);
    }

    private String getFormattedRecipients(String[] recipients) {
        if(recipients == null) return null;
        return Arrays.stream(recipients).collect(Collectors.joining(","));
    }
}
