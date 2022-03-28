package io.springbatch.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class JobExecutionDeciderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
            .start(step())
            .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("startStep")
            .tasklet((contribution, chunkContext) -> {
                System.out.println(">> This is the start tasklet");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println(">>EvenStep has executed");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println(">>OddStep has executed");
                                     return RepeatStatus.FINISHED;
                                 }).build();
    }
}
