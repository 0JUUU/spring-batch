package io.springbatch.springbatch;

import java.util.LinkedList;
import java.util.List;
import org.springframework.aop.support.AopUtils;
import org.springframework.batch.item.ItemReader;

public class LinkedListItemReader<T> implements ItemReader<T> {

    private List<T> list;

    public LinkedListItemReader(List<T> list) {
        if (AopUtils.isAopProxy(list)) {
            this.list = list;
        }
        else {
            this.list = new LinkedList<>(list);
        }
    }

    @Override
    public T read() throws CustomRetryException {

        if (!list.isEmpty()) {
            T remove = (T)list.remove(0);
            return remove;
        }
        return null;
    }
}
