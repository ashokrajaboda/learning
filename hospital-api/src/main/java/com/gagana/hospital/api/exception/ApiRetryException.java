package com.gagana.hospital.api.exception;

public class ApiRetryException extends ApiException {

    private static final long serialVersionUID = -7056256271379383388L;

    public ApiRetryException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ApiRetryException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
