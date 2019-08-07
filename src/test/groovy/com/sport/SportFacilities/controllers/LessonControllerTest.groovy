package com.sport.SportFacilities.controllers

import com.sport.SportFacilities.exceptions.LessonNotFoundException
import com.sport.SportFacilities.exceptions.SportObjectNotFoundException
import com.sport.SportFacilities.models.*
import com.sport.SportFacilities.services.LessonService
import com.sport.SportFacilities.utils.HateoasUtils
import org.apache.tomcat.jni.Local
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class LessonControllerTest extends Specification {

    @Shared
    Instructor instructor = new Instructor("Name", "Surname", "0999234994")
    @Shared
    SwimmingPool swimmingPool = new SwimmingPool(1, new SwimmingPool())
    @Shared
    SwimmingPool swimmingPoolWithSportObjectId1 = new SwimmingPool(1,1,1,new SportObject(1,new SportObject()))

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
    Lesson lessonWithSportObjectId1

    @Shared
    Lesson lessonWithLessonTypeButterfly

    def setup() {
        hateoasUtils = Mock()
        lessonService = Mock()
        lessonToAdd = Lesson.builder()
                .instructor(new Instructor())
                .swimmingPool(new SwimmingPool())
                .customers(new HashSet<Customer>())
                .orderDate(LocalDate.now())
                .build()
        lessonWithId1 = new Lesson(1, lessonToAdd)
        lessonWithInstructor1 = new Lesson(2, lessonToAdd).setInstructor(instructor)
        lessonWithLessonTypeButterfly = new Lesson(3, lessonToAdd)
                .setLessonDetail(new LessonDetail(LessonType.BUTTERFLY, 0f, LocalDate.now()))
        lessonWithSportObjectId1 = new Lesson(3,lessonToAdd).setSwimmingPool(swimmingPoolWithSportObjectId1)
    }

    def "Check if getAllLessons method return all Lessons"() {
        given:
        lessonService.getAllLessons() >> lessonsInDb
        LessonController lessonController = new LessonController(lessonService)
        when:
        ResponseEntity response = lessonController.getAllLessons()
        then:
        response == ResponseEntity.ok(lessonsInDb)
    }

    @Unroll
    def "Check if getAllLessonsByLessonType returns correct object and ignore case in input string"() {
        given:
        lessonService.getAllByLessonsByLessonType(LessonType.BUTTERFLY) >> [lessonWithLessonTypeButterfly]
        LessonController lessonController = new LessonController(lessonService)
        when:
        ResponseEntity response = lessonController.getAllLessonsByLessonType(lessonType)
        then:
        response.getBody()[0] == lessonWithLessonTypeButterfly
        where:
        lessonType << ["butterfly", "Butterfly", "BUTTERFLY", "buTteRfly"]
    }

    @Unroll
    def "Check if getAllLessonsByInstructorId returns correct object"() {
        given:
        lessonService.getAllLessonsByInstructorId(1) >> [lessonWithInstructor1]
        LessonController lessonController = new LessonController(lessonService)
        when:
        ResponseEntity response = lessonController.getAllLessonsByInstructorId(1)
        then:
        response.getBody()[0] == lessonWithInstructor1
    }

    @Unroll
    def "Check if getAllLessonsByOrderDate return correct object"() {
        given:
            Lesson lessonWithOrderDate = new Lesson(LocalDate.parse(orderDate),instructor,swimmingPool)
            lessonWithOrderDate.setCustomers(new HashSet<Customer>())
            lessonService.getAllLessonsByOrderDate(LocalDate.parse(orderDate)) >> [lessonWithOrderDate]
            LessonController lessonController = new LessonController(lessonService)
        when:
            ResponseEntity response = lessonController.getAllLessonsByOrderDate(orderDate)
        then:
            response.getBody()[0] == lessonWithOrderDate
        where:
            orderDate << [LocalDate.now().toString(), LocalDate.now().plusMonths(2).toString()]
    }

    @Unroll
    def "Check if getAllLessonsBySportObjectId return correct object"() {
        given:
        lessonService.getAllLessonsBySportObjectId(1) >> [lessonWithSportObjectId1]
        LessonController lessonController = new LessonController(lessonService)
        when:
        ResponseEntity response = lessonController.getAllLessonsBySportObjectId(1)
        then:
        response.getBody()[0] == lessonWithSportObjectId1
    }

    def "Check if getLessonById method return response with proper content and links"() {
        given:
        Link linkForGettingAllLessons = linkTo(methodOn(LessonController.class).getAllLessons())
                .withRel("all-lessons")
        lessonService.getLessonById(1) >> lessonWithId1
        LessonController lessonController = new LessonController(lessonService)
        when:
        ResponseEntity response = lessonController.getLessonById(1)
        then:
        response.getBody()["content"] == lessonWithId1
        response.getBody()["links"][0] == linkForGettingAllLessons
    }

    def "Getting request for lesson with is not exists should throw exception"() {
        given:
        lessonService.getLessonById(1) >> { throw new LessonNotFoundException() }
        LessonController lessonController = new LessonController(lessonService)
        when:
        lessonService.getLessonById(1)
        then:
        thrown(LessonNotFoundException)
    }

    def "Check if creation of lesson return proper response with uri"() {
        given:
        hateoasUtils.getUriWithPathAndParams(_, _) >> URI.create("uri")
        lessonService.createLesson(lessonToAdd) >> lessonToAdd
        lessonToAdd.setId(1)
        Link linkToUpdate = linkTo(methodOn(LessonController.class).editLesson(1, lessonToAdd))
                .withRel("edit-current-object")
        LessonController lessonController = new LessonController(lessonService, hateoasUtils)
        when:
        ResponseEntity response = lessonController.createLesson(lessonToAdd)
        then:
        response.statusCode == HttpStatus.CREATED
        response.getBody()["content"] == lessonToAdd
        response.getBody()["links"][0] == linkToUpdate
        response.getHeaders()["Location"][0].toURI() == URI.create("uri")
    }

    def "Check if editLesson method calls proper service method"() {
        given:
        hateoasUtils.getUriWithPathAndParams(_, _) >> URI.create("uri")
        LessonController lessonController = new LessonController(lessonService, hateoasUtils)
        when:
        lessonController.editLesson(1, lessonWithId1)
        then:
        1 * lessonService.editLesson(1, lessonWithId1)
    }

    def "Check if deleteLesson method calls proper service method"() {
        given:
        LessonController lessonController = new LessonController(lessonService)
        when:
        lessonController.deleteLesson(1)
        then:
        1 * lessonService.deleteLessonById(1)
    }
}
