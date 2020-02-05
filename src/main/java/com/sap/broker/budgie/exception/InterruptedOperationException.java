package com.sap.broker.budgie.exception;

public class InterruptedOperationException extends RuntimeException {

    public InterruptedOperationException() {
    }

    public InterruptedOperationException(String message) {
        super(message);
    }

    public InterruptedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterruptedOperationException(Throwable cause) {
        super(cause);
    }
}
