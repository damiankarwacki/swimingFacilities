package com.sport.SportFacilities.exceptions;

import java.util.NoSuchElementException;

public class SportObjectNotFoundException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public SportObjectNotFoundException(String parameterName, String parameterValue) {
        super("Cannot find sportObject with " + parameterName + "=" + parameterValue);
    }

    public SportObjectNotFoundException() {
    }
}
