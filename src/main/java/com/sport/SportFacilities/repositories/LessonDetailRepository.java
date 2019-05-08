package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.models.LessonDetail;
import com.sport.SportFacilities.models.LessonType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonDetailRepository extends CrudRepository<LessonDetail, Integer> {
    Optional<LessonDetail> findByPrice(Float price);
    Optional<LessonDetail> findByLessonType(LessonType lessonType);
    
}
