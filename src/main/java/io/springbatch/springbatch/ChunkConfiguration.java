package io.springbatch.springbatch;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ChunkConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                                .incrementer(new RunIdIncrementer())
                                .start(step1())
                                .next(step2())
                                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<String, String>chunk(5)
            .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
            .processor(new ItemProcessor<String, String>() {
                @Override
                public String process(String item) throws Exception {
                    Thread.sleep(300);  // 0.3초 주기로 Item을 처리하도록 함
                    System.out.println("item = " + item);
                    return "my" + item;
                }
            })
            .writer(new ItemWriter<String>() {
                @Override
                public void write(List<? extends String> items) throws Exception {
                    // 일괄처리를 할 수 있도록 List Type
                    Thread.sleep(300);
                    System.out.println("items = " + items);
                }
            })
                                 .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                                 .tasklet((contribution, chunkContext) -> {
                                     System.out.println("step2 has executed");
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }
}
