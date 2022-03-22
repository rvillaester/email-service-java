package com.emailservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MailgunResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("message")
    private String message;

    public MailgunResponse(){}

    public MailgunResponse(String id, String message){
        this.id = id;
        this.message = message;
    }
}
