package com.bannylog.api.exception;

public abstract class BannylogException extends RuntimeException {

    public BannylogException(String message) {
        super(message);
    }

    public BannylogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
