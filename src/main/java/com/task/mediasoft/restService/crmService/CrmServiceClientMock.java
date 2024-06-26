package com.task.mediasoft.restService.crmService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * Класс-заглушка для клиента службы валюты.
 * Этот сервис используется для предоставления мок-данных о курсах валют.
 * Является первичным бином, который используется в случае, если включена опция использования мок-сервиса (включена в конфигурации приложения).
 * Кеширование данных включено для этого сервиса.
 * Данные о курсах валют генерируются случайным образом при каждом запросе.
 */
@Slf4j
@Primary
@Service
@ConditionalOnProperty(name = "rest.crm-service.mock.enabled", matchIfMissing = false)
public class CrmServiceClientMock implements CrmService {

    private static final int codeLength = 10;
    private static final Random random = new Random();

    private static String generateRandomCode() {
        StringBuilder code = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }

    @Override
    public CompletableFuture<Map<String, String>> getInns(List<String> logins) {
        log.info("Mock crm service is used");
        return CompletableFuture.supplyAsync(() -> {
            Map<String, String> loginCodes = new HashMap<>();
            for (String login : logins) {
                loginCodes.put(login, generateRandomCode());
            }

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return loginCodes;
        });
    }

    @Override
    public String getInn(String login) {
        return generateRandomCode();
    }
}