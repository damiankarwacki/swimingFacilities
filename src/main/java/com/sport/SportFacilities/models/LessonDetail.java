package com.sport.SportFacilities.models;

import javax.persistence.*;
import java.time.LocalDate;

public class LessonDetail {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private LessonType lessonType;

    @OneToOne
    private Instructor instructor;

    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    private SwimmingPool swimmingPool;

    private LocalDate date;
}
