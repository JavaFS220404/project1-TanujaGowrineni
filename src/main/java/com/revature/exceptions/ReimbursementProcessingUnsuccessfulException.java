package com.revature.exceptions;

public class ReimbursementProcessingUnsuccessfulException extends RuntimeException {

    public ReimbursementProcessingUnsuccessfulException() {
        super();
    }

    public ReimbursementProcessingUnsuccessfulException(String message) {
        super(message);
    }

    public ReimbursementProcessingUnsuccessfulException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReimbursementProcessingUnsuccessfulException(Throwable cause) {
        super(cause);
    }

    public ReimbursementProcessingUnsuccessfulException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
