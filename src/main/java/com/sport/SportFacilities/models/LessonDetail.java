package com.sport.SportFacilities.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class LessonDetail {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Lesson lesson;
    
    @Enumerated(EnumType.STRING)
    private LessonType lessonType;
    
    private Float price;
    
    @Temporal(TemporalType.DATE)
    private LocalDate date;
}
