package com.task.mediasoft.restService.accountService;

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
@ConditionalOnProperty(name = "rest.account-service.mock.enabled", matchIfMissing = false)
public class AccountServiceClientMock implements AccountService {

    private static final int codeLength = 8;
    private static final Random random = new Random();

    @Override
    public CompletableFuture<Map<String, String>> getAccounts(List<String> logins) {
        // Создаем CompletableFuture, который будет содержать результат запроса
        CompletableFuture<Map<String, String>> future = new CompletableFuture<>();

        // В отдельном потоке генерируем данные
        new Thread(() -> {
            // Генерация случайного кода для каждого логина
            Map<String, String> loginCodes = new HashMap<>();
            for (String login : logins) {
                loginCodes.put(login, generateRandomCode());
            }

            // Имитация задержки запроса
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                // Если поток был прерван, отмечаем CompletableFuture как завершенный с исключением
                future.completeExceptionally(e);
                return;
            }

            // После завершения задержки завершаем CompletableFuture с результатом
            future.complete(loginCodes);
        }).start();

        return future;
    }

    private static String generateRandomCode() {
        StringBuilder code = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }
}