package io.springbatch.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ValidateConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                                     .start(step1())
                                     .next(step2())
                                     .next(step3())
//                                     .validator(new CustomJobParametersValidator())
                                     .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
                                     .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("step1 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("step2 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("step3 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
}
