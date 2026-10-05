package com.studentgradesystem.exception;

public class DuplicateSubjectException extends RuntimeException {

    public DuplicateSubjectException(String message) {
        super(message);
    }
}