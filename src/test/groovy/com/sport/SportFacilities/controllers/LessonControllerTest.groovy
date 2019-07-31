package com.sport.SportFacilities.controllers

import com.sport.SportFacilities.exceptions.SportObjectNotFoundException
import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.Instructor
import com.sport.SportFacilities.models.Lesson
import com.sport.SportFacilities.models.LessonDetail
import com.sport.SportFacilities.models.LessonType
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.models.SwimmingPool
import com.sport.SportFacilities.services.LessonService
import com.sport.SportFacilities.services.SportObjectService
import com.sport.SportFacilities.utils.HateoasUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class LessonControllerTest extends Specification {

    @Shared
    Instructor instructor = new Instructor("Name","Surname","0999234994")
    @Shared
    SwimmingPool swimmingPool = new SwimmingPool(1, new SwimmingPool())

    @Shared
    LessonService lessonService
    @Shared
    HateoasUtils hateoasUtils
    @Shared
    Set<Lesson> lessonsInDb
    @Shared
    Lesson lessonToAdd
    @Shared
    Lesson lessonWithId1
    @Shared
    Lesson lessonWithInstructor1

    @Shared
    Lesson lessonWithLessonTypeButterfly

    def setup() {
        hateoasUtils = Mock()
        lessonService = Mock()
        lessonToAdd = Lesson.builder()
                .instructor(new Instructor())
                .swimmingPool(new SwimmingPool())
                .orderDate(LocalDate.now())
                .build()
        lessonWithId1 = new Lesson(1, lessonToAdd)
        lessonWithInstructor1 = new Lesson(2, lessonToAdd).setInstructor(instructor)
        lessonWithLessonTypeButterfly = new Lesson(3, lessonToAdd)
                .setLessonDetail(new LessonDetail(LessonType.BUTTERFLY,0f,LocalDate.now()))
    }

    //TODO Dokończyć testy Damian
    def "Check if getAllLessons method return all Lessons"() {
        given:
        lessonService.getAllLessons() >> lessonsInDb
        LessonController lessonController = new LessonController(lessonService)
        when:
        ResponseEntity response = sportObjectController.getAllSportObjects()
        then:
        response == ResponseEntity.ok(sportObjectsInDb)
    }

    def "Check if getAllSportObjectFromCity method return proper sportObject"() {
        given:
        sportObjectService.getAllSportObjectsByCity("city2") >> [sportObjectInCity2]
        SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
        ResponseEntity response = sportObjectController.getAllSportObjectFromCity("city2")
        then:
        response.getBody()[0] == sportObjectInCity2
    }

    def "Getting request for sport objects in not existing city should throw exception"() {
        given:
        sportObjectService.getAllSportObjectsByCity("NoExistingCity") >> {throw new SportObjectNotFoundException()}
        SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
        sportObjectController.getAllSportObjectFromCity("NoExistingCity")
        then:
        thrown(SportObjectNotFoundException)
    }

    def "Check if getSportObjectById method return response with proper content and links"() {
        given:
        Link linkForGettingAllSportObjects = linkTo(methodOn(SportObjectController.class).getAllSportObjects())
                .withRel("all-sport-objects")
        sportObjectService.getSportObjectById(1) >> sportObjectWithId1
        SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
        ResponseEntity response = sportObjectController.getSportObjectById(1)
        then:
        response.getBody()["content"] == sportObjectWithId1
        response.getBody()["links"][0] == linkForGettingAllSportObjects
    }

    def "Getting request for sport object with is not exists should throw exception"() {
        given:
        sportObjectService.getSportObjectById(2) >> {throw new SportObjectNotFoundException()}
        SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
        sportObjectController.getSportObjectById(2)
        then:
        thrown(SportObjectNotFoundException)
    }

    def "Check if creation of sport object return proper response with uri"() {
        given:
        hateoasUtils.getUriWithPathAndParams(_,_) >> URI.create("uri")
        sportObjectService.createNewSportObject(sportObjectToAdd) >> sportObjectToAdd
        sportObjectToAdd.setId(1)
        Link linkToUpdate = linkTo(methodOn(SportObjectController.class).editSportObject(1,sportObjectToAdd))
                .withRel("edit-current-object")
        SportObjectController sportObjectController = new SportObjectController(sportObjectService,hateoasUtils)
        when:
        ResponseEntity response = sportObjectController.createSportObject(sportObjectToAdd)
        then:
        response.statusCode == HttpStatus.CREATED
        response.getBody()["content"] == sportObjectToAdd
        response.getBody()["links"][0] == linkToUpdate
        response.getHeaders()["Location"][0].toURI() == URI.create("uri")
    }

    def "Check if editSportObject method calls proper service method"() {
        given:
        hateoasUtils.getUriWithPathAndParams(_,_) >> URI.create("uri")
        SportObjectController sportObjectController = new SportObjectController(sportObjectService, hateoasUtils)
        when:
        sportObjectController.editSportObject(1, sportObjectWithId1)
        then:
        1 * sportObjectService.editSportObject(1, sportObjectWithId1)
    }

    def "Check if deleteSportObject method calls proper service method"() {
        given:
        SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
        sportObjectController.deleteSportObject(1)
        then:
        1 * sportObjectService.deleteSportObjectById(1)
    }
}
