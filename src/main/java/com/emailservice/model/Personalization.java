package com.emailservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Personalization {
    private EmailRecord from;
    private List<EmailRecord> to;
    private List<EmailRecord> cc;
    private List<EmailRecord> bcc;

    public void addTo(EmailRecord record) {
        if(to == null) to = new ArrayList<>();
        to.add(record);
    }

    public void addCc(EmailRecord record) {
        if(cc == null) cc = new ArrayList<>();
        cc.add(record);
    }

    public void addBcc(EmailRecord record) {
        if(bcc == null) bcc = new ArrayList<>();
        bcc.add(record);
    }
}
