package com.sport.SportFacilities.models;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
