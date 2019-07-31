package com.sport.SportFacilities.exceptions;

import com.sport.SportFacilities.models.LessonType;

import java.util.NoSuchElementException;

public class LessonTypeNotFoundException extends NoSuchElementException {

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public LessonTypeNotFoundException(String lessonType) {
        super("Cannot find LessonType: " + lessonType + ".\n Available lesson Types are" + LessonType.values().toString());
    }

    public LessonTypeNotFoundException() {
    }
}
