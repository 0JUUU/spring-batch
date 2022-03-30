package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatCallback;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RepeatConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
            .incrementer(new RunIdIncrementer())
            .start(step1())
            .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
            .<String, String>chunk(5)
            .reader(new ItemReader<String>() {
                int i = 0;

                @Override
                public String read()
                    throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                    i++;
                    return i > 3 ? null : "item" + i;
                }
            })
            .processor(new ItemProcessor<String, String>() {

                RepeatTemplate repeatTemplate = new RepeatTemplate();

                @Override
                public String process(String item) throws Exception {
                    // 1. CompletionPolicy
//                    repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3));
//                    repeatTemplate.setCompletionPolicy(
//                        new TimeoutTerminationPolicy(3000)); // 3초동안 진행하다가 3초가 지나면 끝
//
//                    // 여러개의 completionPolicy 등록 -> 설정한 조건에 먼저 부합하면 바로 종료 (or 조건인 것)
//                    CompositeCompletionPolicy completionPolicy = new CompositeCompletionPolicy();
//                    CompletionPolicy[] completionPolicies = new CompletionPolicy[]{
//                        new SimpleCompletionPolicy(3),
//                        new TimeoutTerminationPolicy(3000)};
//
//                    completionPolicy.setPolicies(completionPolicies);
//                    repeatTemplate.setCompletionPolicy(completionPolicy);

                    // 2. ExceptionHandler
                    repeatTemplate.setExceptionHandler(simpleLimitExceptionHandler());  // 몇 번의 예외가 발생하면 종료
                    repeatTemplate.iterate(new RepeatCallback() {
                        int i = 0;

                        @Override
                        public RepeatStatus doInIteration(RepeatContext context) throws Exception {
                            i++;
                            System.out.println("repeatTemplate testing " + i);
//                            throw new RuntimeException(">> Exception is occurred");
                            // 3. RepeatStatus
//                            return RepeatStatus.CONTINUABLE;
                            return RepeatStatus.FINISHED;
                        }
                    });
                    return item;
                }
            })
            .writer(System.out::println)
            .build();
    }

    @Bean
    public ExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(3);
    }

}
