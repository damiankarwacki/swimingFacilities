package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.exceptions.NoSuchInstructorException;
import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

//TODO Krzychu stworzyć controller, stworzyć wyjątek, dopisać testy jednostkowe do obługi błedów
@Service
public class InstructorService {
    private InstructorRepository instructorRepository;
    
    @Autowired
    public InstructorService(InstructorRepository InstructorRepository) {
        this.instructorRepository = InstructorRepository;
    }
    
    public Instructor createInstructor(Instructor Instructor){
        return instructorRepository.save(Instructor);
    }

    public void deleteInstructor(Instructor Instructor){
        instructorRepository.delete(Instructor);
    }
    
    public Instructor editInstructor(Instructor Instructor){
        return instructorRepository.save(Instructor);
    }
    
    public Instructor getInstructorById(Integer id){
        return instructorRepository.findById(id).orElseThrow(() -> new NoSuchInstructorException("id", id));
    }
    
    public Set<Instructor> getAllInstructors(){
        return Sets.newHashSet(instructorRepository.findAll());
    }
}
