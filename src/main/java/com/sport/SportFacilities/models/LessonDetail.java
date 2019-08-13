package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class LessonDetail {
    
    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @OneToOne(mappedBy = "lessonDetail")
    private Lesson lesson;
 
    @NonNull
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private LessonType lessonType;

    @Positive
    @NonNull
    @Getter
    @Setter
    private Float price;

    @FutureOrPresent
    @NonNull
    @Getter
    @Setter
    private LocalDate date;

    public LessonDetail(Integer id, LessonDetail lessonDetail) {
        this.id = id;
        this.lessonType = lessonDetail.getLessonType();
        this.price = lessonDetail.getPrice();
        this.date = lessonDetail.getDate();
    }
}
