package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
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
    
    @NonNull
    @Getter
    private Float price;

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
