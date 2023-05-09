package com.bannylog.api.exception;

import lombok.Getter;

/**
 * ststus -> 400
 */
@Getter
public class InvalidRequest extends BannylogException {

    private static final String MESSAGE = "잘못된 요청입니다.";

//    public String fieldName;
//    public String message;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
//        this.fieldName = fieldName;
//        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
