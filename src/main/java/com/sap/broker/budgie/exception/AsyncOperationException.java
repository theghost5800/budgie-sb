package com.sap.broker.budgie.exception;

public class AsyncOperationException extends RuntimeException {

    public AsyncOperationException() {
    }

    public AsyncOperationException(String message) {
        super(message);
    }

    public AsyncOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AsyncOperationException(Throwable cause) {
        super(cause);
    }
}
