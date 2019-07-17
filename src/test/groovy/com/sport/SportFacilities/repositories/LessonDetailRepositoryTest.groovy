package com.sport.SportFacilities.repositories

import com.sport.SportFacilities.models.LessonDetail
import com.sport.SportFacilities.models.LessonType
import com.sport.SportFacilities.repositories.LessonDetailRepository
import org.assertj.core.util.Sets
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import javax.transaction.Transactional
import java.sql.SQLException
import java.time.LocalDate


@SpringBootTest
@Stepwise
class LessonDetailRepositoryTest extends Specification {

    @Autowired
    LessonDetailRepository lessonDetailRepository

    LocalDate orderDate = LocalDate.now()
    LocalDate orderDate1 = LocalDate.now().plusDays(1)
    LocalDate orderDate2 = LocalDate.now().plusDays(2)
    LessonType lessonType = LessonType.CRAWL
    LessonType lessonType1 = LessonType.BUTTERFLY
    LessonType lessonType2 = LessonType.FROG

    @Shared
    LessonDetail lessonDetail = new LessonDetail(lessonType, 122, orderDate1)
    @Shared
    LessonDetail lessonDetail1 = new LessonDetail(lessonType1, 123, orderDate1)
    @Shared
    LessonDetail lessonDetail2 = new LessonDetail(lessonType2, 124, orderDate2)

    @Shared
    Set<LessonDetail> lessonDetails

    def setup(){
        lessonDetails = [lessonDetail, lessonDetail1, lessonDetail2]
        lessonDetails.stream().forEach{ld -> lessonDetailRepository.save(ld)}
    }


    def "Check if database is up"(){
        when:
        lessonDetailRepository.existsById(1)
        then:
        notThrown(SQLException)

    }


    def cleanup(){
        lessonDetails = Collections.emptySet()
    }

    def "should return all lesson details by given price"(){
        when:
            Set<LessonDetail> lessonDetailsFromDb = lessonDetailRepository.findAllByPrice(122).orElse(Collections.emptySet())
        then:
            lessonDetailsFromDb == Sets.newLinkedHashSet(lessonDetail)
    }

// should return exception if given price is negative

// should return all in a given price range

// should return all with a given lesson type

}
