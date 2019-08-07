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

    private final SportObject sportObject1 = new SportObject("Object1",new Address("Street", "City", "99-999"))
    private final SportObject sportObject2 = new SportObject("Object2",new Address("Street", "City", "99-999"))
    private final SwimmingPool swimmingPoolOnSportObject1 = new SwimmingPool(4,50,2, sportObject1)
    private final SwimmingPool swimmingPoolOnSportObject2 = new SwimmingPool(4,50,2, sportObject2)
    private final Instructor instructor1 = new Instructor("name1","surname1","phone1")
    private final Instructor instructor2 = new Instructor("name2","surname2","phone2")
    private final LocalDate orderDate = LocalDate.now()
    private final LocalDate orderDate1 = LocalDate.now().plusDays(1)
    private final LessonType lessonType = LessonType.CRAWL
    private final LessonType lessonType1 = LessonType.BUTTERFLY
    private final LessonDetail lessonDetail = new LessonDetail(lessonType, 123f, orderDate)
    private final LessonDetail lessonDetail1 = new LessonDetail(lessonType1, 123f, orderDate1)

    @Shared
    private Lesson lessonOnObject1WithInstructor1
    @Shared
    private Lesson lessonOnObject2WithInstructor2
    @Shared
    private Lesson lessonOnObject2WithInstructor2WithOrderDate1
    @Shared
    private Lesson lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail
    @Shared
    private Lesson lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1
    @Shared
    private Set<Lesson> lessons
    
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
                customers(new HashSet<>()).
                build()
        lessonOnObject1WithInstructor1.setCustomer(new Customer())
        lessonOnObject2WithInstructor2 = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                customers(new HashSet<>()).
                orderDate(orderDate).
                build()
        lessonOnObject2WithInstructor2.setCustomer(new Customer())
        lessonOnObject2WithInstructor2WithOrderDate1 = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                customers(new HashSet<>()).
                orderDate(orderDate1).
                build()
        lessonOnObject2WithInstructor2WithOrderDate1.setCustomer(new Customer())
        lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                orderDate(orderDate1).
                customers(new HashSet<>()).
                lessonDetail(lessonDetail).
                build()
        lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail.setCustomer(new Customer())
        lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1 = Lesson.builder().
                instructor(instructor2).
                swimmingPool(swimmingPoolOnSportObject2).
                orderDate(orderDate1).
                customers(new HashSet<>()).
                lessonDetail(lessonDetail1).
                build()
        lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1.setCustomer(new Customer())
        
        lessons = [lessonOnObject1WithInstructor1, lessonOnObject2WithInstructor2, lessonOnObject2WithInstructor2WithOrderDate1, lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail, lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1]
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
    def "should return all lessons in a given type"(){
        given:
            lessons.stream().forEach { l -> lessonRepository.save(l) }
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByLessonType(lessonType).orElse(Collections.emptySet())
        then:
            lessonsFromDb == Sets.newLinkedHashSet(lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail)
    }

    @Transactional
    def "should return all lessons ordered at given date"(){
        given:
            lessons.stream().forEach { l -> lessonRepository.save(l) }
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByOrderDate(orderDate1).orElse(Collections.emptySet())
        then:
            lessonsFromDb == Sets.newLinkedHashSet(lessonOnObject2WithInstructor2WithOrderDate1,lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail,lessonOnObject2WithInstructor2WithOrderDate1AndLessonDetail1)
    }

    @Transactional
    def "should return all lessons on given sport object"() {
        given:
            lessons.stream().forEach { l -> lessonRepository.save(l) }
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllBySportObject(sportObject1).orElse(Collections.emptySet())
        then:
            lessonsFromDb == Sets.newLinkedHashSet(lessonOnObject1WithInstructor1)
    }

    @Transactional
    def "should return all given instructor lessons"() {
        given:
            lessons.stream().forEach { l -> lessonRepository.save(l) }
        when:
            Set<Lesson> lessonsFromDb = lessonRepository.findAllByInstructor(instructor1).orElse(Collections.emptySet())
        then:
            lessonsFromDb ==  Sets.newLinkedHashSet(lessonOnObject1WithInstructor1)
    }
    
}
