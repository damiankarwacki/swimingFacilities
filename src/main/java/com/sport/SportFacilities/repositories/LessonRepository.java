package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {

    @Query("SELECT l FROM Lesson l JOIN LessonDetail d ON l.lessonDetail = d WHERE d.lessonType = :lessonType")
    Optional<Set<Lesson>> findAllByLessonType(@Param("lessonType") LessonType lessonType);

    Optional<Set<Lesson>> findAllByOrderDate(LocalDate orderDate);

    @Query("SELECT l FROM Lesson l JOIN SwimmingPool s ON l.swimmingPool = s WHERE s.sportObject = :sportObject")
    Optional<Set<Lesson>> findAllBySportObject(@Param("sportObject") SportObject sportObject);

    Optional<Set<Lesson>> findAllByInstructor(Instructor instructor);

}
