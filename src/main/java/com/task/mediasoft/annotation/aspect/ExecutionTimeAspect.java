package com.task.mediasoft.annotation.aspect;

import com.task.mediasoft.annotation.MeasureExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Аспект для измерения времени выполнения методов, аннотированных с помощью {@link MeasureExecutionTime}.
 * Этот аспект регистрирует время выполнения в секундах после завершения метода.
 */
@Slf4j
@Component
@Aspect
public class ExecutionTimeAspect {

    /**
     * Around, который перехватывает методы, аннотированные с помощью {@code MeasureExecutionTime}, и измеряет их время выполнения.
     * Этот метод регистрирует время выполнения метода после его завершения.
     *
     * @param joinPoint точка соединения, предоставляющая доступ к выполнению метода.
     * @return результат выполнения метода.
     * @throws Throwable если во время выполнения метода или обработки аспекта произошла ошибка.
     *                   Это включает в себя любые исключения, выброшенные целевым методом.
     */
    @Around("@annotation(com.task.mediasoft.annotation.MeasureExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed();

        long endTime = System.nanoTime();
        long executionTimeInNanos = endTime - startTime;
        Signature signature = joinPoint.getSignature();

        double executionTimeInSeconds = executionTimeInNanos / 1e9;
        log.info("{} execution time: {}s", signature.toShortString(), executionTimeInSeconds);

        return result;
    }
}