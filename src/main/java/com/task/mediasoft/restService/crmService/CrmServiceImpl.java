package com.task.mediasoft.restService.crmService;

import com.task.mediasoft.configuration.properties.crmService.CrmServiceProperties;
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
public class CrmServiceImpl implements CrmService {

    /**
     * Конфигурационные свойства для взаимодействия с API CRM.
     */
    private final CrmServiceProperties crmServiceProperties;

    /**
     * WebClient для взаимодействия с внешним API.
     */
    private final WebClient crmServiceWebClient;

    /**
     * Получает данные о ИНН от внешнего API.
     *
     * @param logins Список логинов, для которых нужно получить данные ИНН.
     * @return CompletableFuture с картой, где ключом является логин, а значением - соответствующая информация об ИНН.
     */
    public CompletableFuture<Map<String, String>> getInns(List<String> logins) {
        log.info("Impl crm service is used");
        return this.crmServiceWebClient.post()
                .uri(crmServiceProperties.getMethods().getGetInn())
                .body(Mono.just(logins), List.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .toFuture();
    }
}
