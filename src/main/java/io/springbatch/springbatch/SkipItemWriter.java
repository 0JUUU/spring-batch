package io.springbatch.springbatch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

public class SkipItemWriter implements ItemWriter<String> {

    private int cnt = 0;

    @Override
    public void write(List<? extends String> items) throws Exception {
        for (String item : items) {
            System.out.println("ItemWriter : " + item);
            if (item.equals("-12")) {
                cnt++;
                throw new SkippableException("Write failed. cnt : " + cnt);
            }
        }
    }
}
