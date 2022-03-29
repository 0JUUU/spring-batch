package io.springbatch.springbatch;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

public class CustomItemWriter implements ItemStreamWriter<String> {

    @Override
    public void write(List<? extends String> items) throws Exception {
        items.forEach(System.out::println);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("CustomItemWriter.open");
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("CustomItemWriter.update");
    }

    @Override
    public void close() throws ItemStreamException {
        System.out.println("CustomItemWriter.close");
    }
}
