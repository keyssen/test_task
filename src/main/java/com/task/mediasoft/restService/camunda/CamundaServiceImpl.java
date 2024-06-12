package com.task.mediasoft.restService.camunda;

import com.task.mediasoft.configuration.properties.camundaService.CamundaServiceProperties;
import com.task.mediasoft.order.model.dto.StartConfirmDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaServiceImpl implements CamundaService {

    private final CamundaServiceProperties camundaServiceProperties;

    /**
     * WebClient для взаимодействия с внешним API.
     */
    private final WebClient camundaServiceWebClient;


    public String startConfirm(StartConfirmDTO startConfirmDTO) {
        log.info("Impl camunda service is used");
        return this.camundaServiceWebClient.post()
                .uri(camundaServiceProperties.getMethods().getStartConfirm())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(startConfirmDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
