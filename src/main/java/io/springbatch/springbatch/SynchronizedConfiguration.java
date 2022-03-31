package io.springbatch.springbatch;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class SynchronizedConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
            .incrementer(new RunIdIncrementer())
            .start(step1())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<Customer, Customer>chunk(100)
            .reader(customItemReader())
            .listener(new ItemReadListener<Customer>() {
                @Override
                public void beforeRead() {

                }

                @Override
                public void afterRead(Customer item) {
                    System.out.println("Thread : " + Thread.currentThread().getName() + " >> item.getId() = " + item.getId());
                }

                @Override
                public void onReadError(Exception ex) {

                }
            })
            .writer(customerItemWriter())
            .taskExecutor(taskExecutor())
            .build();
    }

    @Bean
    @StepScope
    public SynchronizedItemStreamReader<Customer> customItemReader() {
        JdbcCursorItemReader<Customer> notSafetyReader = new JdbcCursorItemReaderBuilder<Customer>()
            .fetchSize(100)
            .dataSource(dataSource)
            .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
            .sql("select id, firstName, lastName, birthdate from customer")
            .name("NotSafetyReader")
            .build();

        return new SynchronizedItemStreamReaderBuilder<Customer>()
            .delegate(notSafetyReader)
            .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Customer> customerItemWriter() {
        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("insert into customer2 values (:id, :firstName, :lastName, :birthdate)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("safety-thread-");
        return executor;
    }
}
