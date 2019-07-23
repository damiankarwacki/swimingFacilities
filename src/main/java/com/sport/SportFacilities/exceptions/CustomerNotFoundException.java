package com.sport.SportFacilities.exceptions;

import java.util.NoSuchElementException;

public class CustomerNotFoundException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }

    public CustomerNotFoundException(String parameterName, String parameterValue){
        super("Cannot find Customer with " + parameterName + "=" + parameterValue);
    }

    public CustomerNotFoundException() {
    }
}
