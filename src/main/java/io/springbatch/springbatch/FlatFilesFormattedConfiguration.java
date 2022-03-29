package io.springbatch.springbatch;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@RequiredArgsConstructor
@Configuration
public class FlatFilesFormattedConfiguration {

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
            .<String,String>chunk(10)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build();
    }

    @Bean
    public ListItemReader customItemReader() {

        List<Customer> customers = Arrays.asList(new Customer(1, "hong gil dong1", 41),
            new Customer(2, "hong gil dong2", 42),
            new Customer(3, "hong gil dong3", 43));

        ListItemReader<Customer> reader = new ListItemReader<>(customers);
        return reader;
    }

    @Bean
    public FlatFileItemWriter<Customer> customItemWriter() throws Exception {
        return new FlatFileItemWriterBuilder<Customer>()
            .name("flatFileWriter")
            .resource(new FileSystemResource("/Users/a1101717/Desktop/inflearn/스프링 배치 - Sping Boot 기반으로 개발하는 Spring Batch/spring-batch/src/main/resources/customer.txt"))
            .formatted()
            .format("%-2d%-15s%-2d")
            .names(new String[]{"id", "name", "age"})
            .build();
    }
}
