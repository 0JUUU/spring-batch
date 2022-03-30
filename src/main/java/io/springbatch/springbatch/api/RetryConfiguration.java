//package io.springbatch.springbatch.api;
//
//import io.springbatch.springbatch.RetryableException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.retry.RetryPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//
//@RequiredArgsConstructor
//@Configuration
//public class RetryConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public Job job() throws Exception {
//        return jobBuilderFactory.get("batchJob")
//            .incrementer(new RunIdIncrementer())
//            .start(step1())
//            .build();
//    }
//
//    @Bean
//    public Step step1() throws Exception {
//        return stepBuilderFactory.get("step1")
//            .<String, String>chunk(5)
//            .reader(reader())
//            .processor(processor())
//            .writer(items -> items.forEach(System.out::println))
//            .faultTolerant()
//            .skip(RetryableException.class)
//            .skipLimit(2)
////            .retry(RetryableException.class)
////            .retryLimit(2)
//            .retryPolicy(retryPolicy())
//            .build();
//    }
//
//    @Bean
//    public ItemProcessor<? super String, String> processor() {
//        return new RetryItemProcessor();
//    }
//
//    @Bean
//    public ListItemReader<String> reader() {
//        List<String> items = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            items.add(String.valueOf(i));
//        }
//        return new ListItemReader<>(items);
//    }
//
//    @Bean
//    public RetryPolicy retryPolicy() {
//        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
//        exceptionClass.put(RetryableException.class, true);
//
//        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(2, exceptionClass);
//        return simpleRetryPolicy;
//    }
//}
