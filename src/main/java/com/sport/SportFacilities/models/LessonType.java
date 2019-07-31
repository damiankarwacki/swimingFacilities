package com.sport.SportFacilities.models;

import com.sport.SportFacilities.exceptions.LessonTypeNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum  LessonType {
    CRAWL,
    BUTTERFLY,
    BACK,
    FROG;


    public static LessonType fromString(String lessonType){
        return Optional.ofNullable(stringToLessonType.get(lessonType.toUpperCase()))
                .orElseThrow(() -> new LessonTypeNotFoundException(lessonType));
    }

    private static final Map<String, LessonType> stringToLessonType =
            Stream.of(values()).collect(Collectors.toMap(e -> e.toString(), e -> e));
}
