package com.klub.jobs.scheduler.exception;

public class ForbiddenException extends Exception{

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
