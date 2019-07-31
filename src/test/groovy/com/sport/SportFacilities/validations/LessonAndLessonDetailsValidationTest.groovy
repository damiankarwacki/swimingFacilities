package com.sport.SportFacilities.validations

import com.sport.SportFacilities.models.*
import com.sport.SportFacilities.repositories.InstructorRepository
import com.sport.SportFacilities.repositories.LessonRepository
import com.sport.SportFacilities.repositories.SwimmingPoolRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.TransactionSystemException
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolationException
import java.time.LocalDate

@SpringBootTest
class LessonAndLessonDetailsValidationTest extends Specification {

    @Shared
    private SportObject sportObject = new SportObject("SportObject", new Address("Street", "City", "99-999"))
    @Shared
    private SwimmingPool swimmingPool = new SwimmingPool(4, 50, 2, sportObject)
    @Shared
    private Instructor instructor = new Instructor("name1", "surname1", "phone1")

    @Autowired
    LessonRepository lessonRepository

    @Autowired
    SwimmingPoolRepository swimmingPoolRepository

    @Autowired
    InstructorRepository instructorRepository

    @Transactional
    def setup() {
        instructorRepository.save(instructor)
        swimmingPoolRepository.save(swimmingPool)
    }

    @Unroll
    def "Order date should be present or placed in future"() {
        given:
            Lesson lesson = Lesson.builder()
                .orderDate(orderDate)
                .lessonDetail(new LessonDetail())
                .instructor(instructor)
                .swimmingPool(swimmingPool)
                .build()
        when:
            lessonRepository.save(lesson)
        then:
            notThrown(ConstraintViolationException)
        where:
            orderDate <<
                [LocalDate.now(),
                 LocalDate.now().plusDays(1),
                 LocalDate.now().plusMonths(1)]
    }

    @Unroll
    def "ConstraintViolationException should be thrown if order date is not present or placed in future"() {
        given:
            Lesson lesson = Lesson.builder()
                .orderDate(orderDate)
                .lessonDetail(new LessonDetail())
                .instructor(instructor)
                .swimmingPool(swimmingPool)
                .build()
        when:
            lessonRepository.save(lesson)
        then:
            thrown(RuntimeException)
        where:
            orderDate <<
                [LocalDate.now().minusDays(1),
                 LocalDate.now().minusMonths(1)]
    }
}