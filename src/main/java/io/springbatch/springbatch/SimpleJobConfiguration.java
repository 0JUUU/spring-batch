package io.springbatch.springbatch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                                .start(step1())
                                .next(step2())
                                .next(step3())
                                .incrementer(new RunIdIncrementer())
                                .validator(new JobParametersValidator() {
                                    @Override
                                    public void validate(JobParameters parameters) throws JobParametersInvalidException {
                                    }
                                })
                                .preventRestart()
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println(">> step1 has executed");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println(">> step2 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                                 .tasklet((contribution, chunkContext) -> {
                                     chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
                                     contribution.setExitStatus(ExitStatus.STOPPED);
                                     System.out.println(">> step3 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
}
