package com.sport.SportFacilities.services

import com.sport.SportFacilities.models.Instructor
import com.sport.SportFacilities.repositories.InstructorRepository
import spock.lang.Shared
import spock.lang.Specification

class InstructorServiceTest extends Specification {
    
    @Shared InstructorRepository instructorRepository
    @Shared Instructor instructor
    
    def setup(){
        instructorRepository = Mock()
        instructor = new Instructor("name","surname","phone")
    }
    
    def "Create instructor method should return instructor with newly created id"() {
        given:
            instructorRepository.save(instructor) >> new Instructor(1,instructor)
            InstructorService instructorService = new InstructorService(instructorRepository)
        when:
            Instructor resultInstructor = instructorService.createInstructor(instructor)
        then:
            resultInstructor.getId() == 1
    }
    
    def "Edit instructor method should return the same instructor that is provided"() {
        given:
            Instructor editedInstructor = new Instructor(1,instructor)
            instructorRepository.save(editedInstructor) >> editedInstructor
            InstructorService instructorService = new InstructorService(instructorRepository)
        when:
            Instructor resultInstructor = instructorService.editInstructor(editedInstructor)
        then:
            resultInstructor.equals(editedInstructor)
    }
    
    def "Get by id method should find instructor with given id"() {
        given:
            instructorRepository.findById(1) >> Optional.of(new Instructor(1,instructor))
            instructorRepository.findById(2) >> Optional.of(new Instructor(2, instructor))
            InstructorService instructorService = new InstructorService(instructorRepository)
        when:
            Instructor instructor1 = instructorService.getInstructorById(1)
            Instructor instructor2 = instructorService.getInstructorById(2)
        then:
            instructor1.getId() == 1
            instructor2.getId() == 2
    }
    
    def "Get by id method should throw an exception if instructor with given id cannot be found"() {
        given:
            instructorRepository.findById(1) >> Optional.ofNullable(null)
            InstructorService instructorService = new InstructorService(instructorRepository)
        when:
            instructorService.getInstructorById(1)
        then:
            thrown(NoSuchElementException)
    }
    
    def "Get all instructors should return all instructors from database"() {
        given:
            Set<Instructor> givenInstructores = [new Instructor(1,instructor), new Instructor(2,instructor)]
            instructorRepository.findAll() >> givenInstructores
            InstructorService instructorService = new InstructorService(instructorRepository)
        when:
            Set<Instructor> resultInstructores = instructorService.getAllInstructors()
        then:
            resultInstructores == givenInstructores
    }
}
