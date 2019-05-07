package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.Lesson;
import com.sport.SportFacilities.models.LessonType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {
    
    @Query("SELECT l FROM Lesson l JOIN l.lessonDetail d WHERE d.lessonType = :lessonType")
    Optional<Lesson> findByLessonType(@Param("lessonType") LessonType lessonType);
    
    Optional<Lesson> findAllByOrderDate(LocalDate orderDate);
    @Query("SELECT l FROM Lesson l JOIN l.swimmingPool s JOIN s.sportObject o WHERE o.id = :sportObjectId")
    
    Optional<Lesson> findAllBySportObjectId(@Param("sportObjectId") Integer sportObjectId);
}
