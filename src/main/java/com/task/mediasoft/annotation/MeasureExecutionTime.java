package com.task.mediasoft.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация MeasureExecutionTime используется для пометки методов,
 * время выполнения которых требуется измерить.
 * Методы, помеченные этой аннотацией, будут проанализированы
 * для определения времени выполнения.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MeasureExecutionTime {
}
