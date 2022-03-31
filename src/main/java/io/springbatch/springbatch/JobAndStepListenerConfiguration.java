package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class JobAndStepListenerConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CustomStepExecutionListener customStepExecutionListener;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
            .incrementer(new RunIdIncrementer())
            .start(step1())
            .next(step2())
//            .listener(new CustomJobExecutionListener())
            .listener(new CustomAnnotationJobExecutionListener())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(
                    StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        throw new RuntimeException("failed");
                    return RepeatStatus.FINISHED;
                }
            })
            .listener(customStepExecutionListener)
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    return RepeatStatus.FINISHED;
                }
            })
            .listener(customStepExecutionListener)
            .build();
    }
}