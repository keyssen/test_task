package com.task.mediasoft.order.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class StartConfirmDTO {
    private final UUID id;
    private final String deliveryAddress;
    private final String inn;
    private final String accountNumber;
    private final BigDecimal totalPrice;
    private final String userLogin;
}
