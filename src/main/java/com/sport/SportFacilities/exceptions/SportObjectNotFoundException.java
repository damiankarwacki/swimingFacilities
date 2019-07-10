package com.sport.SportFacilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SportObjectNotFoundException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public SportObjectNotFoundException(String parameterName, String parameterValue) {
        super("Cannot find sportObject with " + parameterName + "=" + parameterValue);
    }
}
