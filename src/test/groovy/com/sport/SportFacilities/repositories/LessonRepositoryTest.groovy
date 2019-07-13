package com.sport.SportFacilities.repositories

import com.sport.SportFacilities.models.*
import org.assertj.core.util.Sets
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import java.sql.SQLException
import java.time.LocalDate

@SpringBootTest
@Stepwise
class LessonRepositoryTest extends Specification {
    
    @Autowired
    LessonRepository lessonRepository
    @Autowired
    InstructorRepository instructorRepository
    @Autowired
    SwimmingPoolRepository swimmingPoolRepository
    
    SportObject sportObject1 = new SportObject("Object1",new Address("street", "city", "code"))
    SportObject sportObject2 = new SportObject("Object2",new Address("street", "city", "code"))
    SwimmingPool swimmingPoolOnSportObject1 = new SwimmingPool(4,50,2, sportObject1)
    SwimmingPool swimmingPoolOnSportObject2 = new SwimmingPool(4,50,2, sportObject2)
    Instructor instructor1 = new Instructor("name1","surname1","phone1")
    Instructor instructor2 = new Instructor("name2","surname2","phone2")
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
        
        instructorRepository.save(instructor1)
        instructorRepository.save(instructor2)
        swimmingPoolRepository.save(swimmingPoolOnSportObject1)
        swimmingPoolRepository.save(swimmingPoolOnSportObject2)

        
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
    
    def "Check if database is up"(){
        when:
            lessonRepository.existsById(1)
        then:
            notThrown(SQLException)
            
    }

    @Transactional
    @PendingFeature
    def "should return all lessons in a given type"(){
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByLessonDetailLessonType(lessonType).orElse(Collections.emptySet())
        then:
            lessonsFromDb ==  Sets.newLinkedHashSet(lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail)
    }

    @Transactional
    @PendingFeature
    //TODO Niepoprawne wyniki - zwraca encje które nie istnieją w bazie danych
    def "should return all lessons ordered at given date"(){
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByOrderDate(orderDate1).orElse(Collections.emptySet())
        then:
            lessonsFromDb ==  Sets.newLinkedHashSet(lessonOnObject2WithInstructor2WithOrderDate1,lessonOnObject2WithInstructor2WithOrderDate1,lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1)
    }

    @Transactional
    def "should return all lessons on given sport object"() {
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllBySportObject(sportObject1).orElse(Collections.emptySet())
        then:
            lessonsFromDb == Sets.newLinkedHashSet(lessonOnObject1WithInstructor1)
    }

    @Transactional
    def "should return all given instructor lessons"() {
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByInstructor(instructor1).orElse(Collections.emptySet())
        then:
            lessonsFromDb ==  Sets.newLinkedHashSet(lessonOnObject1WithInstructor1)
    }
    
}
