package io.springbatch.springbatch;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SkipConfiguration {

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
                public String read() throws SkippableException {
                    i++;
                    if(i == 3) {
                        throw new SkippableException("skip");
                    }
                    System.out.println("ItemReader : " + i);
                    return i > 20 ? null : String.valueOf(i);
                }
            })
            .processor(itemProcessor())
            .writer(itemWriter())
            .faultTolerant()
//            .skip(SkippableException.class)
//            .skipLimit(3)
//            .skipPolicy(limitCheckingItemSkipPolicy())
            .skipPolicy(new AlwaysSkipItemSkipPolicy())
            .build();
    }

    @Bean
    public SkipItemProcessor itemProcessor() {
        return new SkipItemProcessor();
    }

    @Bean
    public SkipItemWriter itemWriter() {
        return new SkipItemWriter();
    }

    @Bean
    public SkipPolicy limitCheckingItemSkipPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(SkippableException.class, true);

        LimitCheckingItemSkipPolicy limitCheckingItemSkipPolicy = new LimitCheckingItemSkipPolicy(3, exceptionClass);
        return limitCheckingItemSkipPolicy;
    }
}