package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Lesson {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @NonNull
    @Getter
    private LocalDate orderDate;
    
    @ManyToMany(mappedBy = "lessons")
    private Set<Customer> customers;
    
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "lesson_detail_id", referencedColumnName = "id")
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
}
