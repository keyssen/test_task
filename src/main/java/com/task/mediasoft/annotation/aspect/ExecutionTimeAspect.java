package com.task.mediasoft.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ExecutionTimeAspect {

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