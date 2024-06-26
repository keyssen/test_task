package com.task.mediasoft.restService.camunda;

import com.task.mediasoft.order.model.dto.StartConfirmDTO;

public interface CamundaService {
    String startConfirm(StartConfirmDTO startConfirmDTO);
}