package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.exceptions.InstructorNotFoundException;
import com.sport.SportFacilities.models.Instructor;
import com.sport.SportFacilities.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

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

    public Set<Instructor> getAllInstructors(){
        return Sets.newHashSet(instructorRepository.findAll());
    }

    public Instructor getInstructorById(Integer id){
        return instructorRepository.findById(id).orElseThrow(()-> new InstructorNotFoundException("id", String.valueOf(id)));
    }

    public Instructor editInstructor(Instructor instructor, Integer id){
        instructor.setId(id);
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Instructor Instructor){
        instructorRepository.delete(Instructor);
    }
}
