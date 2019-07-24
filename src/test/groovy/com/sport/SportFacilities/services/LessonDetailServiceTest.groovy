package groovy.com.sport.SportFacilities.services

import com.sport.SportFacilities.models.LessonDetail
import com.sport.SportFacilities.models.LessonType
import com.sport.SportFacilities.repositories.LessonDetailRepository
import com.sport.SportFacilities.services.LessonDetailService
import org.assertj.core.util.Sets
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class LessonDetailServiceTest extends Specification{

    @Shared LessonDetailRepository lessonDetailRepository
    @Shared LessonDetail lessonDetail
    @Shared LessonDetail lessonDetail1
    @Shared LessonDetailService lessonDetailService


    def setup(){
        lessonDetailRepository = Mock()
        lessonDetail = new LessonDetail(LessonType.CRAWL, 123f, LocalDate.now())
        lessonDetail1 = new LessonDetail(LessonType.CRAWL, 150f, LocalDate.now())
    }

    def "creates lessonDetail and returns object that was created with new id"(){
        given:
            lessonDetailRepository.save(lessonDetail) >> new LessonDetail(1, lessonDetail)
            lessonDetailService = new LessonDetailService(lessonDetailRepository)
        when:
            LessonDetail newLessonDetail = lessonDetailService.createLessonDetail(lessonDetail)
        then:
            newLessonDetail.getId() == 1
    }

    def "returns all lesson details"(){
        given:
            lessonDetailRepository.findAll() >> Sets.newLinkedHashSet(lessonDetail)
            lessonDetailService = new LessonDetailService(lessonDetailRepository)
        when:
            Set<LessonDetail> lessonDetailSet = lessonDetailService.getAllLessonDetails()
        then:
            lessonDetailSet == Sets.newLinkedHashSet(lessonDetail)
    }

    def "returns lessonDetail with given id"(){
        given:
            lessonDetailRepository.findById(1) >> Optional.of(new LessonDetail(1, lessonDetail))
            lessonDetailService = new LessonDetailService(lessonDetailRepository)
        when:
            LessonDetail resultLessonDetail = lessonDetailService.getLessonDetailById(1)
        then:
            resultLessonDetail.getId() == 1
    }

    def "returns all lessonDetails at given price"(){
        given:
            Set<LessonDetail> lessonDetailsAtGivenPrice = [new LessonDetail(1, lessonDetail), new LessonDetail(2, lessonDetail)]
            lessonDetailRepository.findAllByPrice(123f) >> lessonDetailsAtGivenPrice
            lessonDetailService = new LessonDetailService(lessonDetailRepository)
        when:
            Set<LessonDetail> lessonDetailSet = lessonDetailService.getAllLessonDetailsByPrice(123f)
        then:
            lessonDetailSet == lessonDetailsAtGivenPrice
    }

    def "returns all lessonDetails at given price range"(){
        given:
            Set<LessonDetail> lessonDetailsAtGivenPriceRange = [new LessonDetail(1, lessonDetail), new LessonDetail(2, lessonDetail1)]
            lessonDetailRepository.findAllByPriceBetween(123f, 150f) >> lessonDetailsAtGivenPriceRange
            lessonDetailService = new LessonDetailService(lessonDetailRepository)
        when:
            Set<LessonDetail> lessonDetailSet = lessonDetailService.getAllLessonDetailsByPriceBetween(123f, 150f)
        then:
            lessonDetailSet == lessonDetailsAtGivenPriceRange
    }

    def "returns all lessonDetails at given LessonType"(){
        given:
            Set<LessonDetail> lessonDetailsAtGivenLessonType = [new LessonDetail(1, lessonDetail), new LessonDetail(2, lessonDetail1)]
            lessonDetailRepository.findAllByLessonType(LessonType.CRAWL) >> lessonDetailsAtGivenLessonType
            lessonDetailService = new LessonDetailService(lessonDetailRepository)
        when:
            Set<LessonDetail> lessonDetailSet = lessonDetailService.getAllByLessonType(LessonType.CRAWL)
        then:
            lessonDetailSet == lessonDetailsAtGivenLessonType

    }

    def "returns freshly edited LessonDetail"(){
        given:
            LessonDetail editedLessonDetail = new LessonDetail(1, lessonDetail)
            lessonDetailRepository.save(editedLessonDetail) >> editedLessonDetail
            lessonDetailService = new LessonDetailService(lessonDetailRepository)
        when:
            LessonDetail resultLessonDetail = lessonDetailService.editLessonDetail(editedLessonDetail)
        then:
            resultLessonDetail.getId() == 1
    }


}
