package com.sport.SportFacilities.repositories;

import com.sport.SportFacilities.models.Instructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
}
