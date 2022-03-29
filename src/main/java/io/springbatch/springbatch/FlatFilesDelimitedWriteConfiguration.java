package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class FlatFilesDelimitedWriteConfiguration {

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
            .<String,String>chunk(1)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build();
    }

    @Bean
    public ItemReader customItemReader() {
        List<Customer> customers = Arrays.asList(new Customer(1, "hong gil dong", 41),
            new Customer(2, "hong gil dong2", 41),
            new Customer(3, "hong gil dong", 41));

        ListItemReader<Customer> reader = new ListItemReader<>(customers);
        return reader;
    }

    @Bean
    public ItemWriter<? super Customer> customItemWriter() {
        return new FlatFileItemWriterBuilder<>()
            .name("flatFileWriter")
            .resource(new FileSystemResource("/Users/a1101717/Desktop/inflearn/스프링 배치 - Sping Boot 기반으로 개발하는 Spring Batch/spring-batch/src/main/resources/customer.txt"))
            .delimited()
            .delimiter("|")
            .names(new String[] {"id", "name", "age"})
            .build();
    }

    /*@Bean
    public FlatFileItemWriter<Customer> customItemWriter() throws Exception {

        BeanWrapperFieldExtractor<Customer> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id","name","age"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<Customer> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<Customer>()
                .name("CustomerWriter")
                .resource(new ClassPathResource("customer.csv"))
                .lineAggregator(lineAggregator)
                .build();
    }*/
}