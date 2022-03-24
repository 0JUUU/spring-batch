package io.springbatch.springbatch;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class JobParameterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                                .start(step1())
                                .next(step2())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                                 .tasklet(new Tasklet() {
                                     @Override
                                     public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                                         // Stepcontribution을 이용한 방식
                                         // 실제로 우리가 전달한 파라미터값을 확인할 수 있는 방식
                                         JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
                                         jobParameters.getString("name");
                                         jobParameters.getLong("seq");
                                         jobParameters.getDate("date");
                                         jobParameters.getDouble("age");

                                         // chunkContext를 이용
                                         // 위와 동일한 값을 얻을 수 있음
                                         // 하지만, 이 방식은 단순히 Map에서 값을 꺼내오는 것 (Map으로 고정)
                                         // JobParameters 값을 바꾼다면, 여기는 반영 x => 단순히 값을 꺼내오는 것
                                         Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();

                                         System.out.println("step1 was executed");
                                         return RepeatStatus.FINISHED;
                                     }
                                 })
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet(new Tasklet() {
                                     @Override
                                     public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                                         System.out.println("step2 was executed");
                                         return RepeatStatus.FINISHED;
                                     }
                                 })
                                 .build();
    }
}
