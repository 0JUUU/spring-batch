package io.springbatch.springbatch.batch.job.api;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ApiJobChildConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Step apiMasterStep;
    private final JobLauncher jobLauncher;

    @Bean
    public Step jobStep() throws Exception {
        return stepBuilderFactory.get("jobStep")
            .job(childJob())
            .launcher(jobLauncher)
            .build();
    }

    @Bean
    public Job childJob() throws Exception {
        return jobBuilderFactory.get("childJob")
            .start(apiMasterStep)
            .build();
    }
}
