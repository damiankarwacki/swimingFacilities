package com.sport.SportFacilities.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class LessonDetail {
    
    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @OneToOne(
            mappedBy = "lessonDetail"
    )
    private Lesson lesson;
 
    @NonNull
    @Enumerated(EnumType.STRING)
    @Getter
    private LessonType lessonType;
    
    @NonNull
    @Getter
    private Float price;

    @NonNull
    private LocalDate date;
}
