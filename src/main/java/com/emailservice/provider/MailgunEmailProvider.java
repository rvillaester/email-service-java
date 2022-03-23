package com.emailservice.provider;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.EmailResponse;
import com.emailservice.model.MailgunResponse;
import com.emailservice.model.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Order(1)
@Component
@Setter
public class MailgunEmailProvider extends EmailProvider{

    @Autowired
    @Qualifier("mailgunHttpClient")
    private HttpClient httpClient;

    @Override
    public HttpRequestBase buildHttpRequest(EmailRequest emailRequest) {
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("from", "Admin<admin@mailgun.org>"));
        form.add(new BasicNameValuePair("to", emailRequest.getFormattedRecipients()));
        if(emailRequest.getCcRecipients() != null) form.add(new BasicNameValuePair("cc", emailRequest.getFormattedCCRecipients()));
        if(emailRequest.getBccRecipients() != null) form.add(new BasicNameValuePair("bcc", emailRequest.getFormattedBCCRecipients()));
        form.add(new BasicNameValuePair("subject", emailRequest.getSubject()));
        form.add(new BasicNameValuePair("text", emailRequest.getMessage()));

        HttpPost httpPost = new HttpPost(buildUrl());
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        return httpPost;
    }

    @Override
    public HttpClient getHttpClient() {
        return httpClient;
    }

    private String buildUrl() {
        return String.format("https://api.mailgun.net/v3/%s/messages", System.getenv("MG_DOMAIN"));
    }

    @Override
    public EmailResponse handleSuccess(String body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            MailgunResponse mailgunResponse = mapper.readValue(body, MailgunResponse.class);
            EmailResponse.EmailResponseBuilder responseBuilder = EmailResponse.builder();
            responseBuilder.status(Status.SUCCESS);
            responseBuilder.id(mailgunResponse.getId().replace("<", "").replace(">", ""));
            responseBuilder.message(mailgunResponse.getMessage());
            return responseBuilder.build();
        } catch (IOException e) {
            return EmailResponse.sendFailed();
        }
    }

    @Override
    public String getName() {
        return "Mailgun";
    }
}
