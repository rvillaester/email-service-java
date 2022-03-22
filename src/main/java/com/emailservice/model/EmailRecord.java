package com.emailservice.model;

import lombok.Data;

@Data
public class EmailRecord {
    private String name;
    private String email;

    public static EmailRecord sender = new EmailRecord("sender", "sender@sendgrid.com");

    public EmailRecord(String email){
        this.email = email;
    }

    public EmailRecord(String name, String email){
        this.name = name;
        this.email = email;
    }
}
