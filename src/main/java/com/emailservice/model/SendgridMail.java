package com.emailservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SendgridMail {

    private EmailRecord from;
    private List<Personalization> personalizations;

    public void addPersonalization(Personalization personalization){
        if(personalizations == null) personalizations = new ArrayList<>();
        personalizations.add(personalization);
    }
}