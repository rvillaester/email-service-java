package com.emailservice;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    @Qualifier("defaultHttpClient")
    public HttpClient buildDefaultHttpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    @Qualifier("mailgunHttpClient")
    public HttpClient buildMailgunHttpClient() {
        return HttpClientBuilder.create().setDefaultCredentialsProvider(buildMailgunCredentialsProvider()).build();
    }

    private CredentialsProvider buildMailgunCredentialsProvider() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
//              = new UsernamePasswordCredentials("api", "c7dd7303c0b2fc9a8d868b566a430303-0677517f-fabb0801");
                = new UsernamePasswordCredentials("api", System.getenv("mg-api-key"));
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }
}