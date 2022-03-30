package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ClassifierConfiguration {

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
            .<ProcessorInfo, ProcessorInfo>chunk(10)
            .reader(new ItemReader<ProcessorInfo>() {

                int i = 0;

                @Override
                public ProcessorInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                    i++;
                    ProcessorInfo processorInfo = ProcessorInfo.builder().id(i).build();
                    return i > 3 ? null : processorInfo;
                }
            })
            .writer(System.out::println)
            .build();
    }
}
