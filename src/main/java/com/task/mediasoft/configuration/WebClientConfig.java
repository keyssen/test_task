package com.task.mediasoft.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final Environment env;

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(Objects.requireNonNull(env.getProperty("currency-service.host"))).build();
    }
}
