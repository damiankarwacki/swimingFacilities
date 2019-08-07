package com.sport.SportFacilities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
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
    @Setter
    private Integer id;

    @NonNull
    @Getter
    @FutureOrPresent(message = "{validation.orderDate}")
    private LocalDate orderDate;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "lessons")
    private Set<Customer> customers;

    @Valid
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "lesson_detail_id", referencedColumnName = "id")
    @Getter
    @Setter
    private LessonDetail lessonDetail;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    @Getter
    @Setter
    private Instructor instructor;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swimming_pool_id")
    @Getter
    @Setter
    private SwimmingPool swimmingPool;

    public Lesson(@NonNull LocalDate orderDate, LessonDetail lessonDetail, @NonNull Instructor instructor, @NonNull SwimmingPool swimmingPool) {
        this.orderDate = orderDate;
        this.lessonDetail = lessonDetail;
        this.instructor = instructor;
        this.swimmingPool = swimmingPool;
        this.customers = new HashSet<>();
    }

    public Lesson(Integer id, Lesson lesson) {
        this.id = id;
        this.orderDate = lesson.getOrderDate();
        this.lessonDetail = lesson.getLessonDetail();
        this.instructor = lesson.getInstructor();
        this.swimmingPool = lesson.getSwimmingPool();
        this.customers = new HashSet<>();
    }

    public void setCustomer(Customer customer){
        customers.add(customer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;

        Lesson lesson = (Lesson) o;

        if (id != null ? !id.equals(lesson.id) : lesson.id != null) return false;
        if (!orderDate.equals(lesson.orderDate)) return false;
        if (!customers.equals(lesson.customers)) return false;
        if (lessonDetail != null ? !lessonDetail.equals(lesson.lessonDetail) : lesson.lessonDetail != null)
            return false;
        if (!instructor.equals(lesson.instructor)) return false;
        return swimmingPool.equals(lesson.swimmingPool);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + orderDate.hashCode();
        result = 31 * result + customers.hashCode();
        result = 31 * result + (lessonDetail != null ? lessonDetail.hashCode() : 0);
        result = 31 * result + instructor.hashCode();
        result = 31 * result + swimmingPool.hashCode();
        return result;
    }
}
