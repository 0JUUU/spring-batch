package io.springbatch.springbatch;

import org.springframework.batch.item.ItemProcessor;

public class SkipItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        System.out.println("ItemProcess : " + item);
        if (item.equals("6") || item.equals("7")) {
            cnt++;
            throw new SkippableException(">> Process failed. cnt : " + cnt);
        } else {
            return String.valueOf(Integer.valueOf(item) * -1);
        }
    }
}
