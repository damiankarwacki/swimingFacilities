package com.sport.SportFacilities.repositories

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.Instructor
import com.sport.SportFacilities.models.Lesson
import com.sport.SportFacilities.models.LessonDetail
import com.sport.SportFacilities.models.LessonType
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.models.SwimmingPool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest
class LessonRepositoryTest extends Specification {
    
    @Autowired
    LessonRepository lessonRepository

    @Autowired
    SportObjectRepository sportObjectRepository
    
    SportObject sportObject1 = new SportObject(new Address("street", "city", "code"),"Object1")
    SportObject sportObject2 = new SportObject(new Address("street", "city", "code"), "Object2")
    SwimmingPool swimmingPoolOnSportObject1 = new SwimmingPool(4,50,2, sportObject1)
    SwimmingPool swimmingPoolOnSportObject2 = new SwimmingPool(4,50,2, sportObject2)
    Instructor instructor1 = new Instructor()
    Instructor instructor2 = new Instructor()
    LocalDate orderDate = LocalDate.now()
    LocalDate orderDate1 = LocalDate.now().plusDays(1)
    LessonType lessonType = LessonType.CRAWL
    LessonType lessonType1 = LessonType.BUTTERFLY
    LessonDetail lessonDetail = new LessonDetail(lessonType, 123, orderDate1)
    LessonDetail lessonDetail1 = new LessonDetail(lessonType1, 123, orderDate1)

    @Shared
    Lesson lessonOnObject1WithInstructor1
    @Shared
    Lesson lessonOnObject2WithInstructor2
    @Shared
    Lesson lessonOnObject2WithInstructor2WithOrderDate1
    @Shared
    Lesson lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail
    @Shared
    Lesson lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1
    @Shared
    Set<Lesson> lessons
    
    @Transactional
    def setup(){

        lessonOnObject1WithInstructor1 = Lesson.builder().
                instructor(instructor1).
                swimmingPool(swimmingPoolOnSportObject1).
                orderDate(orderDate).
                build()
        lessonOnObject2WithInstructor2 = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                orderDate(orderDate).
                build()
        lessonOnObject2WithInstructor2WithOrderDate1 = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                orderDate(orderDate1).
                build()
        lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                orderDate(orderDate1).
                lessonDetail(lessonDetail).
                build()
        lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1 = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                orderDate(orderDate1).
                lessonDetail(lessonDetail1).
                build()

        lessons = [lessonOnObject1WithInstructor1, lessonOnObject2WithInstructor2, lessonOnObject2WithInstructor2WithOrderDate1, lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail, lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1]

        lessons.stream().forEach { l -> lessonRepository.save(l) }
    }
    
    def cleanup() {
        lessons = Collections.emptySet()
    }

    @Transactional
    def "should return all lessons in a given type"(){
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByLessonDetailLessonType(lessonType).orElse(Collections.emptySet())
        then:
            lessonsFromDb[0] == lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail
    }

    @Transactional
    def "should return all lessons ordered at given date"(){
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByOrderDate(orderDate1).orElse(Collections.emptySet())
        then:
            lessonsFromDb[0] == lessonOnObject2WithInstructor2WithOrderDate1
    }

    @Transactional
    def "should return all lessons on given sport object"() {
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllBySportObject(sportObject1).orElse(Collections.emptySet())
        then:
            lessonsFromDb[0] == lessonOnObject1WithInstructor1
    }

    @Transactional
    def "should return all given instructor lessons"() {
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByInstructor(instructor1).orElse(Collections.emptySet())
        then:
            lessonsFromDb[0] == lessonOnObject1WithInstructor1
    }
    
}
