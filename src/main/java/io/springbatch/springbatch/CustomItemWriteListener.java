package io.springbatch.springbatch;

import java.util.List;
import org.springframework.batch.core.ItemWriteListener;

public class CustomItemWriteListener implements ItemWriteListener<Customer> {

    @Override
    public void beforeWrite(List<? extends Customer> items) {

    }

    @Override
    public void afterWrite(List<? extends Customer> items) {
        System.out.println(">> Thread : " + Thread.currentThread().getName() + " / write items : " + items.size());
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Customer> items) {

    }
}
