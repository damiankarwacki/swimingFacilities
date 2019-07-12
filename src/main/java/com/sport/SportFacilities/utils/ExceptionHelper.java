package com.sport.SportFacilities.utils;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHelper {

    public static final String extractErrorMessageFromBindingResult(BindingResult bindingResult){
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(e -> errors.add(e.getField() + " - " + cleanMessage(e.getDefaultMessage())));
        bindingResult.getGlobalErrors().stream().forEach(e -> errors.add(e.getObjectName() + " - " + cleanMessage(e.getDefaultMessage())));
        return errors.toString();
    }

    private static final String cleanMessage(String message){
        return message.replaceAll("\"","");
    }
}
