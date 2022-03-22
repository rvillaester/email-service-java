package com.emailservice.provider;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.EmailResponse;
import com.emailservice.model.Status;
import com.emailservice.util.ApplicationUtil;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This is a dummy provider will always return a successful request
 */
@Order
@Component
public class DummyProvider extends EmailProvider{

    @Override
    public String getName() {
        return "Good Provider";
    }

    @Override
    public EmailResponse process(EmailRequest emailRequest) {
        String id = String.valueOf(ApplicationUtil.timeInMillis());
        return EmailResponse.builder().id(id).message("Success").status(Status.SUCCESS).build();
    }

    @Override
    protected EmailResponse handleSuccess(String body) {
        return null;
    }

    @Override
    protected HttpRequestBase buildHttpRequest(EmailRequest emailRequest) {
        return null;
    }
}
