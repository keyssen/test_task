package com.task.mediasoft.restService.camunda;

import com.task.mediasoft.order.model.dto.StartConfirmDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;


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
@ConditionalOnProperty(name = "rest.camunda-service.mock.enabled", matchIfMissing = false)
public class CamundaServiceClientMock implements CamundaService {

    @Override
    public String startConfirm(StartConfirmDTO startConfirmDTO) {
        return UUID.randomUUID().toString();
    }
}