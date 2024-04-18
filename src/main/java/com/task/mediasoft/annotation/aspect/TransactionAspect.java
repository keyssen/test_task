package com.task.mediasoft.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Этот класс служит как аспект для обработки транзакций в приложениях Spring.
 * Он записывает информацию о транзакционных методах и их времени выполнения.
 */
@Aspect
@Component
@Slf4j
public class TransactionAspect {
    /**
     * ThreadLocal для хранения имени вызываемого метода
     */
    private final ThreadLocal<String> callingMethodName = new ThreadLocal<>();

    /**
     * ThreadLocal для хранения времени начала выполнения метода
     */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * Перехватывает выполнение методов, помеченных аннотацией @Transactional,
     * чтобы записать вызов метода и время его начала.
     *
     * @param joinPoint Точка соединения, представляющая перехваченный вызов метода.
     * @return Результат перехваченного вызова метода.
     * @throws Throwable если произошла ошибка во время вызова метода.
     */
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransactionalMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        callingMethodName.set(joinPoint.getSignature().toShortString());
        startTime.set(System.nanoTime());
        return joinPoint.proceed();
    }

    /**
     * Регистрирует TransactionSynchronization для методов, помеченных аннотацией @Transactional,
     * чтобы записать время выполнения метода после фиксации транзакции.
     */
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSynchronization() {
        String methodName = callingMethodName.get();
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                long endTime = System.nanoTime();
                long executionTimeNano = endTime - startTime.get();
                double executionTimeSec = executionTimeNano / 1e9;
                log.info("Transactional method: {} | {} s", methodName, executionTimeSec);
            }
        });
    }
}