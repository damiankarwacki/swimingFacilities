package groovy.com.sport.SportFacilities.controllers

import com.sport.SportFacilities.controllers.InstructorController
import com.sport.SportFacilities.exceptions.InstructorNotFoundException
import com.sport.SportFacilities.models.Instructor
import com.sport.SportFacilities.services.InstructorService
import com.sport.SportFacilities.utils.HateoasUtils
import org.springframework.hateoas.Link
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class InstructorControllerTest extends Specification {

    @Shared
    HateoasUtils hateoasUtils
    @Shared
    InstructorService instructorService
    @Shared
    Instructor instructor1
    @Shared
    Instructor instructor2
    @Shared
    Instructor instructor3
    @Shared
    Instructor instructor1WithId
    @Shared
    URI uri
    @Shared
    Set<Instructor> instructorsFromDB

    def setup(){
        hateoasUtils = Mock()
        instructorService = Mock()
        instructor1 = new Instructor("name1", "surname1", "123123123")
        instructor2 = new Instructor("name2", "surname2", "123123123")
        instructor3 = new Instructor("name3", "surname3", "123123123")
        instructorsFromDB = [instructor1, instructor2, instructor3]
        instructor1WithId = new Instructor(1, instructor1)
        uri = URI.create("uri")
    }

    def "should return created instructor with a proper uri"(){
        given:
            hateoasUtils.getUriWithPathAndParams(_,_) >> uri
            instructorService.createInstructor(instructor1) >> instructor1WithId
            InstructorController instructorController = new InstructorController(instructorService, hateoasUtils)
        when:
            ResponseEntity res = instructorController.createInstructor(instructor1)
        then:
            res == ResponseEntity.created(uri).body(instructor1WithId)
    }

    def "should return all instructors"(){
        given:
            instructorService.getAllInstructors() >> instructorsFromDB
            InstructorController instructorController = new InstructorController(instructorService)
        when:
            ResponseEntity res = instructorController.getAllInstructors()
        then:
            res == ResponseEntity.ok(instructorsFromDB)

    }

    def "should return instructors at given id"(){
        given:
            Link link = linkTo(methodOn(InstructorController.class).getAllInstructors()).withRel("all-instructors");
            instructorService.getInstructorById(1) >> instructor1WithId
            InstructorController instructorController = new InstructorController(instructorService)
        when:
            ResponseEntity res = instructorController.getInstructorById(1)
        then:
            res.getBody()["content"] == instructor1WithId
            res.getBody()["links"][0] == link
    }

    def "should throw exception of no instructor"(){
        given:
            instructorService.getInstructorById(1) >> {throw new InstructorNotFoundException()}
            InstructorController instructorController = new InstructorController(instructorService)
        when:
            instructorController.getInstructorById(1)
        then:
            thrown(InstructorNotFoundException)
    }

    def "should return edited instructor"(){
        given:
            hateoasUtils.getUriWithPathAndParams(_,_) >> uri
            instructorService.editInstructor(instructor1, 1) >> instructor1WithId
            InstructorController instructorController = new InstructorController(instructorService, hateoasUtils)
        when:
            ResponseEntity res = instructorController.editInstructorById(instructor1, 1)
        then:
            res == ResponseEntity.created(uri).body(instructor1)
    }

    def "check if it runs proper methods when delete"(){
        given:
            InstructorController instructorController = new InstructorController(instructorService)
        when:
            instructorController.deleteInstructor(1)
        then:
            1 * instructorService.getInstructorById(1) >> instructor1WithId
            1 * instructorService.deleteInstructor(instructor1WithId)
    }

}
