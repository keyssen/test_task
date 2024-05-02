package com.task.mediasoft.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Конфигурационный класс для настройки кэша в приложении.
 * <p>
 * Этот класс использует библиотеку Caffeine для создания кэша в памяти,
 * который автоматически удаляет элементы через 1 минуту после последней записи.
 * </p>
 */
@Configuration
public class CacheConfig {

    /**
     * Создает и конфигурирует экземпляр {@link Caffeine} для управления кэшем.
     * <p>
     * Этот метод настраивает экземпляр Caffeine для автоматической отчистки элементов
     * через 1 минуту после записи.
     * </p>
     *
     * @return Экземпляр Caffeine.
     */
    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES);
    }

    /**
     * Создает менеджер кэша, используя предоставленный объект Caffeine для конфигурации.
     * <p>
     * Менеджер кэша настроен для использования Caffeine в качестве своего механизма кэширования
     * </p>
     *
     * @param caffeine Предварительно сконфигурированный объект Caffeine, используемый для настройки менеджера кэша.
     * @return Конфигурированный менеджер кэша, готовый к использованию в приложении.
     */
    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}