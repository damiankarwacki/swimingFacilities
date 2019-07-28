package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

//TODO Damian, walidacja pól
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Lesson {
    
    @Id
    @GeneratedValue
    @Getter
    private Integer id;
    
    @NonNull
    @Getter
    @Size
    @FutureOrPresent(message = "{validation.orderDate}")
    private LocalDate orderDate;

    @ManyToMany(mappedBy = "lessons")
    private Set<Customer> customers;

    @Valid
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "lesson_detail_id", referencedColumnName = "id")
    @Getter
    private LessonDetail lessonDetail;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    @Getter
    private Instructor instructor;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    @Getter
    private SwimmingPool swimmingPool;

    public Lesson(@NonNull LocalDate orderDate, LessonDetail lessonDetail, @NonNull Instructor instructor, @NonNull SwimmingPool swimmingPool) {
        this.orderDate = orderDate;
        this.lessonDetail = lessonDetail;
        this.instructor = instructor;
        this.swimmingPool = swimmingPool;
    }

    public Lesson(Integer id, Lesson lesson){
        this.id = id;
        this.orderDate = lesson.getOrderDate();
        this.lessonDetail = lesson.getLessonDetail();
        this.instructor = lesson.getInstructor();
        this.swimmingPool = lesson.getSwimmingPool();
    }
}
