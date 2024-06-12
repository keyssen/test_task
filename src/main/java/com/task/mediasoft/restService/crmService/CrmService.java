package com.task.mediasoft.restService.crmService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CrmService {
    /**
     * Получает данные о ИНН от внешнего API.
     *
     * @param logins Список логинов, для которых нужно получить данные ИНН.
     * @return CompletableFuture с картой, где ключом является логин, а значением - соответствующая информация об ИНН.
     */
    CompletableFuture<Map<String, String>> getInns(List<String> logins);

    String getInn(String login);
}