package com.jfcbxp.supervendedor.config;

import feign.RetryableException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.ReactiveOptions;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactivefeign.client.ReactiveHttpRequestInterceptors;
import reactivefeign.client.statushandler.ReactiveStatusHandler;
import reactivefeign.client.statushandler.ReactiveStatusHandlers;
import reactivefeign.retry.BasicReactiveRetryPolicy;
import reactivefeign.retry.ReactiveRetryPolicy;
import reactivefeign.webclient.WebReactiveOptions;

import java.time.Instant;
import java.util.Date;

@Configuration
public class ForexClientConfig {
    private static final String API_KEY_HEADER = "X-API-KEY";

    @Bean
    public ReactiveOptions reactiveOptions() {
        return new WebReactiveOptions.Builder()
                .setReadTimeoutMillis(2000)
                .setWriteTimeoutMillis(2000)
                .setResponseTimeoutMillis(2000)
                .build();
    }

    @Bean
    public ReactiveHttpRequestInterceptor apiKeyIntercepter() {
        return ReactiveHttpRequestInterceptors.addHeader(API_KEY_HEADER, API_KEY_HEADER);
    }

    @Bean
    public ReactiveStatusHandler reactiveStatusHandler() {
        return ReactiveStatusHandlers.throwOnStatus(
                (status) -> (status == 500),
                (methodKey, response) ->
                        new RetryableException(response.status(), "", null, Date.from(Instant.EPOCH), null));
    }

    @Bean
    public ReactiveRetryPolicy reactiveRetryPolicy() {
        return BasicReactiveRetryPolicy.retryWithBackoff(3, 2000);
    }

}
