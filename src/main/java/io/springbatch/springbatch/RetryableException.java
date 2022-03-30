package io.springbatch.springbatch;

public class RetryableException extends RuntimeException {

    public RetryableException() {
    }

    public RetryableException(String message) {
        super(message);
    }

}
