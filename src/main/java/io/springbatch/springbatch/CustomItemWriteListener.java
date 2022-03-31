package io.springbatch.springbatch;

import java.util.List;
import org.springframework.batch.core.ItemWriteListener;

public class CustomItemWriteListener implements ItemWriteListener {

    @Override
    public void beforeWrite(List items) {
        System.out.println(">> beforeWrite");
    }

    @Override
    public void afterWrite(List items) {
        System.out.println(">> afterWrite : "+ items);
    }

    @Override
    public void onWriteError(Exception exception, List items) {
        System.out.println(">> onWriteError : " + exception.getMessage());
        System.out.println(">> onWriteError : " + items);
    }
}
