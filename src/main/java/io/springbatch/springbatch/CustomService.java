package io.springbatch.springbatch;

public class CustomService<T> {

    private int cnt = 0;

    public T joinCustomer(){
        return (T)("item" + cnt++);
    }
}
