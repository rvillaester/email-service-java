package com.emailservice.provider;

import com.emailservice.exception.ApplicationException;
import com.emailservice.model.*;
import com.emailservice.util.ApplicationUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Order(2)
@Component
public class SendGridEmailProvider extends EmailProvider{

    @Override
    public EmailResponse handleSuccess(String body) {
        String id = String.valueOf(ApplicationUtil.timeInMillis());
        return EmailResponse.builder().message("Success").status(Status.FAILED).id(id).build();
    }

    @Override
    public HttpRequestBase buildHttpRequest(EmailRequest emailRequest) {
        try {
            HttpPost httpPost = new HttpPost("https://api.sendgrid.com//v3/mail/send");
            httpPost.setHeader("Authorization", String.format("Bearer %s", System.getenv("sg-api-key")));
            StringEntity entity = new StringEntity(ApplicationUtil.toJsonString(toSendgridMail(emailRequest)));
            httpPost.setEntity(entity);
            return httpPost;
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(e);
        }
    }

    private SendgridMail toSendgridMail(EmailRequest emailRequest) {
        SendgridMail sendgridMail = new SendgridMail();
        sendgridMail.setFrom(EmailRecord.sender);
        sendgridMail.addPersonalization(createPersonalization(emailRequest));
        return sendgridMail;
    }

    private Personalization createPersonalization(EmailRequest emailRequest) {
        Personalization personalization = new Personalization();
        personalization.setTo(toEmailRecords(emailRequest.getRecipients()));
        personalization.setCc(toEmailRecords(emailRequest.getCcRecipients()));
        personalization.setBcc(toEmailRecords(emailRequest.getBccRecipients()));
        return personalization;
    }

    private List<EmailRecord> toEmailRecords(String[] emails) {
        if(ApplicationUtil.isEmpty(emails)) return null;
        return Arrays.stream(emails).map(email -> new EmailRecord(email)).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return "Sendgrid";
    }

}
