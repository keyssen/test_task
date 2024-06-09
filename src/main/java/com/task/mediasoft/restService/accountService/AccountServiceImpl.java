package com.task.mediasoft.restService.accountService;

import com.task.mediasoft.configuration.properties.accountService.AccountServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    /**
     * Конфигурационные свойства для взаимодействия с API аккаунтов.
     */
    private final AccountServiceProperties accountServiceProperties;

    /**
     * WebClient для взаимодействия с внешним API.
     */
    private final WebClient accountServiceWebClient;

    /**
     * Получает данные о аккаунтах от внешнего API.
     *
     * @param logins Список логинов, для которых нужно получить данные аккаунтов.
     * @return CompletableFuture с картой, где ключом является логин, а значением - соответствующая информация о аккаунте.
     */
    public CompletableFuture<Map<String, String>> getAccounts(List<String> logins) {
        log.info("Impl account service is used");
        return this.accountServiceWebClient.post()
                .uri(accountServiceProperties.getMethods().getGetAccount())
                .body(Mono.just(logins), List.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .toFuture();
    }
}
