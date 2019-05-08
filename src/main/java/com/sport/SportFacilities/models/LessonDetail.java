package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class LessonDetail {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Lesson lesson;
    
    @Enumerated(EnumType.STRING)
    private LessonType lessonType;
    
    private Float price;

    private LocalDate date;
}
