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
                = new UsernamePasswordCredentials("api", System.getenv("MG_API_KEY"));
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }
}