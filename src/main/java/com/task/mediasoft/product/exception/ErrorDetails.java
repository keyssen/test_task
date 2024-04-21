package com.task.mediasoft.product.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class ErrorDetails {
    private String className;
    private String exceptionName;
    private String message;
    private LocalDateTime time;
}
