package io.springbatch.springbatch;

import org.springframework.batch.core.ItemProcessListener;

public class CustomItemProcessorListener implements ItemProcessListener {

    @Override
    public void beforeProcess(Object item) {
        System.out.println(">> beforeProcess");
    }

    @Override
    public void afterProcess(Object item, Object result) {
        System.out.println(">> afterProcess : "+ item);
        System.out.println(">> afterProcess : "+ result);
    }

    @Override
    public void onProcessError(Object item, Exception e) {
        System.out.println(">> onProcessError : " + e.getMessage());
        System.out.println(">> onProcessError : " + item);
    }
}
