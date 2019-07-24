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
    private LessonType lessonType;

    @Positive
    @NonNull
    @Getter
    private Float price;

//  todo pytanie: czy w przypadku lekcji które już się odbyły to będzie kolidowało? czy ta walidacja działa tylko prze tworzeniu obiektu?
    @FutureOrPresent
    @NonNull
    @Getter
    private LocalDate date;

    public LessonDetail(Integer id, LessonDetail lessonDetail) {
        this.id = id;
        this.lessonType = lessonDetail.getLessonType();
        this.price = lessonDetail.getPrice();
        this.date = lessonDetail.getDate();
    }
}
