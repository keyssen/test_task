package com.task.mediasoft.configuration;

import com.task.mediasoft.kafka.hadler.EventHandler;
import com.task.mediasoft.kafka.request.EventSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class EventHandlerConfig {

    @Bean
    <T extends EventSource> Set<EventHandler<T>> eventHandlers(Set<EventHandler<T>> eventHandlers) {
        return new HashSet<>(eventHandlers);
    }
}
