package com.sport.SportFacilities.exceptions;

import com.sport.SportFacilities.models.LessonType;

import java.util.NoSuchElementException;

public class LessonNotFoundException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public LessonNotFoundException(Object id) {
        super("Cannot find Lesson with id" + id);
    }

    public LessonNotFoundException() {
    }
}
