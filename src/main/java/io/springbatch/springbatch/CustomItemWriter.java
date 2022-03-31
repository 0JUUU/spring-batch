package io.springbatch.springbatch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

public class CustomItemWriter implements ItemWriter<String> {
    int count = 0;
    @Override
    public void write(List<? extends String> items) throws CustomRetryException {
        for (String item : items) {
            if(count < 2) {
                if (count % 2 == 0) {
                    count = count + 1;

                } else if (count % 2 == 1) {
                    count = count + 1;
                    throw new CustomRetryException();
                }
            }
            System.out.println("write : " + item);
        }
    }
}

