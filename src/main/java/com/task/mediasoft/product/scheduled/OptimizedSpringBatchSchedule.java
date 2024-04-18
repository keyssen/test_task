package com.task.mediasoft.product.scheduled;


import com.task.mediasoft.annotation.MeasureExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс OptimizedSpringBatchSchedule представляет расписание для оптимизированной обработки данных о продуктах
 * с использованием Spring Batch.
 * Этот компонент выполняется только в профиле "prod" и при условии,
 * что параметры app.scheduling.enabled, app.scheduling.optimization установлены в "true",
 * а app.scheduling.preparedStatement установлены в "false".
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('true') and '${app.scheduling.preparedStatement}'.equals('false')")
@Profile("prod")
public class OptimizedSpringBatchSchedule {

    private final JobLauncher jobLauncher;
    private final Job importUserJob;

    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @MeasureExecutionTime
    @Transactional
    public void scheduleFixedDelayTask() {
        try {
            log.info("OptimizedSchedule: Start.");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(importUserJob, jobParameters);
            log.info("OptimizedSchedule: Batch job successfully triggered.");
        } catch (JobExecutionException e) {
            log.error("OptimizedSchedule: Error triggering batch job: {}", e.getMessage());
        }
    }
}