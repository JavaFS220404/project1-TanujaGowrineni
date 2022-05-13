package com.revature.exceptions;

public class CreateReimbursementUnsuccessulException extends RuntimeException {

    public CreateReimbursementUnsuccessulException() {
        super();
    }

    public CreateReimbursementUnsuccessulException(String message) {
        super(message);
    }

    public CreateReimbursementUnsuccessulException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateReimbursementUnsuccessulException(Throwable cause) {
        super(cause);
    }

    public CreateReimbursementUnsuccessulException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
