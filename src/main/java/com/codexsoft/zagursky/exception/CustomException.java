package com.codexsoft.zagursky.exception;

/**
 * Created by Dzenyaa on 26.01.2018.
 */
public class CustomException extends Throwable {
    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
