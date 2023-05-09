package com.bannylog.api.exception;

/**
 * ststus -> 400
 */
public class InvalidRequest extends BannylogException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
