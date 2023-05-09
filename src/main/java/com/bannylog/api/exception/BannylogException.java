package com.bannylog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BannylogException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public BannylogException(String message) {
        super(message);
    }

    public BannylogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
