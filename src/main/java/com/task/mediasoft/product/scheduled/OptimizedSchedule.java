package com.task.mediasoft.product.scheduled;

import com.task.mediasoft.annotation.MeasureExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('true')")
@Profile("prod")
public class OptimizedSchedule {
    private static final Logger log = LoggerFactory.getLogger(OptimizedSchedule.class);

    private final JobLauncher jobLauncher;
    private final Job importUserJob;

    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    @MeasureExecutionTime
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