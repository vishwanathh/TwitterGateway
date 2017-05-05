package com.ericsson.appiot.gateway.examples.twitter;

public class TwitterApiException extends Exception {

    private static final long serialVersionUID = -4300658557883672803L;

    public TwitterApiException() {
    }

    public TwitterApiException(String message) {
        super(message);
    }

    public TwitterApiException(Throwable cause) {
        super(cause);
    }

    public TwitterApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwitterApiException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
