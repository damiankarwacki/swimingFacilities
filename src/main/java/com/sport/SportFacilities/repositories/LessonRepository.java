package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.models.Lesson;
import com.sport.SportFacilities.models.LessonDetail;
import com.sport.SportFacilities.models.LessonType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {

//    TODO Krzychu testy
    @Query("SELECT l FROM Lesson l JOIN LessonDetail d ON d.id = l.lessonDetail WHERE d.lessonType = :lessonType")
    Optional<Lesson> findByLessonType(@Param("lessonType") LessonType lessonType);
    
    Optional<Set<Lesson>> findAllByOrderDate(LocalDate orderDate);
    
//    TODO Damian testy
    @Query("SELECT l FROM Lesson l JOIN SwimmingPool s ON s.id = l.swimmingPool WHERE s.sportObject = :sportObjectId")
    Optional<Set<Lesson>> findAllBySportObjectId(@Param("sportObjectId") Integer sportObjectId);
    
    Optional<LessonDetail> findByInstructor(Instructor instructor);
    
}
