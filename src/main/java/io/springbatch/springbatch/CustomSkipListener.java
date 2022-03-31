package io.springbatch.springbatch;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
public class CustomSkipListener implements SkipListener<Integer, String> {

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println(">> onSkipRead : " + t.getMessage());
    }

    @Override
    public void onSkipInWrite(String item, Throwable t) {
        System.out.println(">> onSkipWrite : " + item);
        System.out.println(">> onSkipWrite : exception = " + t.getMessage());
    }

    @Override
    public void onSkipInProcess(Integer item, Throwable t) {
        System.out.println(">> onSkipProcess : " + item);
        System.out.println(">> onSkipWrite : exception = " + t.getMessage());
    }
}
