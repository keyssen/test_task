package com.task.mediasoft;

import com.task.mediasoft.configuration.properties.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Основной класс приложения, инициализурующий запуск
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties(ConfigProperties.class)
public class MediasoftApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediasoftApplication.class, args);
    }
}
