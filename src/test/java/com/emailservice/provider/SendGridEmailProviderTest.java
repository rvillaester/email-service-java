package com.emailservice.provider;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.EmailResponse;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendGridEmailProviderTest {

    @InjectMocks private SendGridEmailProvider provider;
    @Mock private HttpClient httpClient;
    @Mock private HttpResponse response;
    @Mock private HttpEntity entity;
    @Mock private StatusLine statusLine;
    private InputStream inputStream;

    private EmailRequest request;

    @BeforeEach
    public void setup() throws IOException{
        inputStream = new CharSequenceInputStream("success", Charset.defaultCharset());
        request = new EmailRequest();
        request.setRecipients(new String[]{"rey@test.com"});
        request.setSubject("subject");
        request.setMessage("message");
        when(httpClient.execute(any(HttpRequestBase.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(entity.getContent()).thenReturn(inputStream);
    }

    @Test
    public void canSendSuccessfulRequest() {
        EmailResponse response = provider.process(request);
        assertTrue(response.isSuccess());
    }

    @Test
    public void failedRequestWhenSendingOfMessageInProviderFails() {
        when(statusLine.getStatusCode()).thenReturn(401);
        EmailResponse response = provider.process(request);
        assertFalse(response.isSuccess());
    }
}
