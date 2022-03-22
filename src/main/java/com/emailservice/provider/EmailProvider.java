package com.emailservice.provider;

import com.emailservice.exception.EmailException;
import com.emailservice.model.EmailRequest;
import com.emailservice.model.EmailResponse;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class EmailProvider {

    @Autowired
    @Qualifier("defaultHttpClient")
    private HttpClient httpClient;

    public abstract String getName();
    protected abstract EmailResponse handleSuccess(String body);
    protected abstract HttpRequestBase buildHttpRequest(EmailRequest emailRequest);

    public EmailResponse process(EmailRequest emailRequest) {
        try {
            HttpRequestBase httpRequest = buildHttpRequest(emailRequest);
            return executeRequest(getHttpClient(), httpRequest);
        } catch (Exception e) {
            System.out.println(String.format("Internal error: %s", e.getMessage()));
            return EmailResponse.internalError();
        }
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }

    protected EmailResponse handleFailed(String body) {
        return EmailResponse.sendFailed(body);
    }

    private String getBody(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            builder.append(inputLine);
            builder.append("\n");
        }
        in.close();
        return builder.toString();
    }

    private EmailResponse executeRequest(HttpClient httpClient, HttpRequestBase httpRequest) {
        try {
            HttpResponse response = httpClient.execute(httpRequest);
            String body = getBody(response.getEntity().getContent());
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                EmailResponse emailResponse = handleSuccess(body);
                System.out.println(String.format("%s post message OK with ID: %s", getName(), emailResponse.getId()));
                return handleSuccess(body);
            } else {
                System.out.println(String.format("%s failed to post message with status %s - %s", getName(), statusCode, body));
                return handleFailed(body);
            }
        }catch(IOException e){
            throw new EmailException(e);
        }
    }
}
