package com.task.mediasoft.restService.accountService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AccountService {
    /**
     * Получает данные о аккаунтах от внешнего API.
     *
     * @param logins Список логинов, для которых нужно получить данные аккаунтов.
     * @return CompletableFuture с картой, где ключом является логин, а значением - соответствующая информация о аккаунте.
     */
    CompletableFuture<Map<String, String>> getAccounts(List<String> logins);

    String getAccount(String login);
}