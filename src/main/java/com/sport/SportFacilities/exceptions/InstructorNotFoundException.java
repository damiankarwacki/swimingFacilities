package com.sport.SportFacilities.exceptions;

import java.util.NoSuchElementException;

public class InstructorNotFoundException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }

    public InstructorNotFoundException(String parameterName, String parameterValue){
        super("Cannot find Instructor with " + parameterName + "=" + parameterValue);
    }

    public InstructorNotFoundException() {
    }
}
