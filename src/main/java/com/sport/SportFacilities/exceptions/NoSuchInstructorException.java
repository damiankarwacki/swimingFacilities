package com.sport.SportFacilities.exceptions;

import java.util.NoSuchElementException;

public class NoSuchInstructorException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public NoSuchInstructorException(String parameterName, Object parameterValue) {
        super("Cannot find instructor with " + parameterName + "=" + parameterValue);
    }

    public NoSuchInstructorException() {
    }
}
