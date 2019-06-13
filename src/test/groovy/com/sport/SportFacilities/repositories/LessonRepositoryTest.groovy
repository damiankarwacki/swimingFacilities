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
    
    SportObject sportObject1 = new SportObject(new Address(),"Object1")
    SportObject sportObject2 = new SportObject(new Address(), "Object2")
    SwimmingPool swimmingPoolOnSportObject1 = new SwimmingPool(4,50,2, sportObject1)
    SwimmingPool swimmingPoolOnSportObject2 = new SwimmingPool(4,50,2, sportObject2)
    Instructor instructor1 = new Instructor()
    Instructor instructor2 = new Instructor()
    LocalDate orderDate = LocalDate.now()
    
    @Shared
    Lesson lessonOnObject1WithInstructor1
    @Shared
    Lesson lessonOnObject2WithInstructor2
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
        lessons = [lessonOnObject1WithInstructor1, lessonOnObject2WithInstructor2]
        lessons.stream().forEach { l -> lessonRepository.save(l) }
    }
    
    def cleanup() {
        lessons = Collections.emptySet()
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
