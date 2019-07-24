package com.sport.SportFacilities.exceptions;

import java.util.NoSuchElementException;

public class SwimmingPoolNotFoundException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public SwimmingPoolNotFoundException() {
    }

    public SwimmingPoolNotFoundException(String parameterName, String parameterValue) {
        super("Cannot find SwimmingPool with: " + parameterName + "=" + parameterValue);
    }
}
