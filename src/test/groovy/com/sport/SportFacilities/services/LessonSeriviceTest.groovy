package groovy.com.sport.SportFacilities.services

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.Instructor
import com.sport.SportFacilities.models.Lesson
import com.sport.SportFacilities.models.LessonDetail
import com.sport.SportFacilities.models.LessonType
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.models.SwimmingPool
import com.sport.SportFacilities.repositories.LessonRepository
import com.sport.SportFacilities.services.LessonService
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class LessonSeriviceTest extends Specification {
    @Shared LessonRepository lessonRepository
    @Shared Lesson lesson1
    @Shared Lesson lesson2
    @Shared LessonDetail lessonDetail1
    @Shared LessonDetail lessonDetail2
    @Shared Instructor instructor1
    @Shared Instructor instructor2
    @Shared SwimmingPool swimmingPool1
    @Shared SwimmingPool swimmingPool2
    @Shared LessonService lessonService
    @Shared LocalDate orderDate1 = LocalDate.now()
    @Shared LocalDate orderDate2 = LocalDate.now().plusDays(3)
    @Shared SportObject sportObject1 = new SportObject("name1", new Address())
    @Shared SportObject sportObject2 = new SportObject("name1", new Address())

    def setup(){
        lessonRepository = Mock()
        instructor1 = new Instructor("name1","surname","phone")
        instructor2 = new Instructor("name2","surname","phone")

        lessonDetail1 = new LessonDetail(LessonType.CRAWL, 123f, orderDate1)
        lessonDetail2 = new LessonDetail(LessonType.CRAWL, 150f, orderDate2)

        swimmingPool1 = new SwimmingPool(5, 25f, 2f, sportObject1)
        swimmingPool2 = new SwimmingPool(5, 25f, 2f, sportObject2)

        lesson1 = new Lesson(LocalDate.now(), lessonDetail1, instructor1, swimmingPool1)
        lesson2 = new Lesson(LocalDate.now(), lessonDetail2, instructor2, swimmingPool2)
    }

    def "should return created lesson"(){
        given:
            lessonRepository.save(lesson1) >> new Lesson(1, lesson1)
            lessonService = new LessonService(lessonRepository)
        when:
            Lesson createdLesson = lessonService.createLesson(lesson1)
        then:
            createdLesson.getId() == 1
    }

    def "should return lesson at given id"(){
        given:
            lessonRepository.findById(1) >> Optional.of(new Lesson(1, lesson1))
            lessonService = new LessonService(lessonRepository)
        when:
            Lesson returnedLesson = lessonService.getLessonById(1)
        then:
            returnedLesson.getId() == 1
    }

    def "should return set of all lessons"(){
        given:
            Set<Lesson> allLessons = [new Lesson(1, lesson1), new Lesson(2, lesson2)]
            lessonRepository.findAll() >> allLessons
            lessonService = new LessonService(lessonRepository)
        when:
            Set<Lesson> returnedLessons = lessonService.getAllLessons()
        then:
            returnedLessons == allLessons
    }

    def "should return set of all lessons at given type"(){
        given:
            Set<Lesson> allCrawlLessons = [new Lesson(1, lesson1), new Lesson(2, lesson2)]
            lessonRepository.findAllByLessonType(LessonType.CRAWL) >> allCrawlLessons
            lessonService = new LessonService(lessonRepository)
        when:
            Set<Lesson> returnedLessons = lessonService.getAllByLessonsByLessonType(LessonType.CRAWL)
        then:
            returnedLessons == allCrawlLessons
    }

    def "should return all lessons at given order date"(){
        given:
            Set<Lesson> allLessonsAtOrderDate1 = [new Lesson(1, lesson1), new Lesson(2, lesson1),]
            lessonRepository.findAllByOrderDate(orderDate1) >> allLessonsAtOrderDate1
            lessonService = new LessonService(lessonRepository)
        when:
            Set<Lesson> returnedLessons = lessonService.getAllLessonsByOrderDate(orderDate1)
        then:
            returnedLessons == allLessonsAtOrderDate1
    }

    def "should return all lessons at given sport object"(){
        given:
            Set<Lesson> allLessonsAtSportObject1 = [new Lesson(1, lesson1), new Lesson(2, lesson1),]
            lessonRepository.findAllBySportObject(sportObject1) >> allLessonsAtSportObject1
            lessonService = new LessonService(lessonRepository)
        when:
            Set<Lesson> returnedLessons = lessonService.getAllLessonsBySportObject(sportObject1)
        then:
            returnedLessons == allLessonsAtSportObject1
    }

    def "should return all lessons with given instructor"(){
        given:
            Set<Lesson> allLessonsAtInstructor1 = [new Lesson(1, lesson1), new Lesson(2, lesson1),]
            lessonRepository.findAllByInstructor(instructor1) >> allLessonsAtInstructor1
            lessonService = new LessonService(lessonRepository)
        when:
            Set<Lesson> returnedLessons = lessonService.getAllLessonsByInstructor(instructor1)
        then:
            returnedLessons == allLessonsAtInstructor1
    }

    def "should return edited lesson"(){
        given:
            Lesson editedLesson = new Lesson(1, lesson2)
            lessonRepository.save(editedLesson) >> editedLesson
            lessonService = new LessonService(lessonRepository)
        when:
            Lesson returnedLesson = lessonService.editLesson(editedLesson)
        then:
            returnedLesson.getId() == 1
    }

}
