package com.gagana.hospital.api.exception;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -2999152801582923123L;
    private final String errorCode;

    public ApiException(final String errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiException(final String errorCode, final String message, final Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
