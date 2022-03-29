package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ItemReaderAdapterConfiguration {

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
    public ItemReader<String> customItemReader() {
        ItemReaderAdapter reader = new ItemReaderAdapter();
        reader.setTargetObject(customService());
        reader.setTargetMethod("joinCustomer");    // 우리가 호출할 메서드명 지정
        return reader;
    }

    private CustomService<String> customService() {
        return new CustomService<>();
    }

    @Bean
    public ItemWriter<String> customItemWriter() {
        return items -> {
            System.out.println(items);
        };
    }
}

