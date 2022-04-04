package io.springbatch.springbatch.batch.job.api;

import io.springbatch.springbatch.batch.listener.JobListener;
import io.springbatch.springbatch.batch.tasklet.ApiEndTasklet;
import io.springbatch.springbatch.batch.tasklet.ApiStartTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ApiJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ApiStartTasklet apiStartTasklet;
    private final ApiEndTasklet apiEndTasklet;
    private final Step jobStep;

    @Bean
    public Job apiJob() throws Exception {
        return jobBuilderFactory.get("apiJob")
            .incrementer(new RunIdIncrementer())
            .listener(new JobListener())
            .start(apiStep1())
            .next(jobStep)
            .next(apiStep2())
            .build();
    }

    @Bean
    public Step apiStep1() throws Exception {
        return stepBuilderFactory.get("apiStep1")
            .tasklet(apiStartTasklet)
            .build();
    }

    @Bean
    public Step apiStep2() throws Exception {
        return stepBuilderFactory.get("apiStep2")
            .tasklet(apiEndTasklet)
            .build();
    }
}
