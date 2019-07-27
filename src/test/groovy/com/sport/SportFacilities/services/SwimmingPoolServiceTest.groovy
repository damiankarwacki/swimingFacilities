package groovy.com.sport.SportFacilities.services

import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.models.SwimmingPool
import com.sport.SportFacilities.repositories.SwimmingPoolRepository
import com.sport.SportFacilities.services.SwimmingPoolService
import spock.lang.Shared
import spock.lang.Specification

class SwimmingPoolServiceTest extends Specification{

    @Shared SwimmingPoolRepository swimmingPoolRepository
    @Shared SwimmingPoolService swimmingPoolService
    @Shared SwimmingPool swimmingPool1
    @Shared SwimmingPool swimmingPool2

    def setup(){
        swimmingPoolRepository = Mock()
        swimmingPool1 = new SwimmingPool(5, 25f, 2f, new SportObject())
        swimmingPool2 = new SwimmingPool(3, 50f, 3f, new SportObject())
    }

    def "should return created swimming pool"(){
        given:
            swimmingPoolRepository.save(swimmingPool1) >> new SwimmingPool(1, swimmingPool1)
            swimmingPoolService = new SwimmingPoolService(swimmingPoolRepository)
        when:
            SwimmingPool returnedSwimmingPool = swimmingPoolService.createSwimmingPool(swimmingPool1)
        then:
            returnedSwimmingPool.getId() == 1
    }

    def "should return swimmming pool at given id"(){
        given:
            swimmingPoolRepository.findById(1) >> Optional.of(new SwimmingPool(1, swimmingPool1))
            swimmingPoolService = new SwimmingPoolService(swimmingPoolRepository)
        when:
            SwimmingPool returnedSwimmingPool = swimmingPoolService.getSwimmingPoolById(1)
        then:
            returnedSwimmingPool.getId() == 1
    }

    def "should return all swimming pools"(){
        given:
            Set<SwimmingPool> allSwimmingPools = [new SwimmingPool(1, swimmingPool1), new SwimmingPool(2, swimmingPool2)]
            swimmingPoolRepository.findAll() >> allSwimmingPools
            swimmingPoolService = new SwimmingPoolService(swimmingPoolRepository)
        when:
            Set<SwimmingPool> returnedSwimmingPoolsSet = swimmingPoolService.getAllSwimmingPools()
        then:
            returnedSwimmingPoolsSet == allSwimmingPools
    }

    def "should return all swimming pools with given depth"(){
        given:
            Set<SwimmingPool> allSwimmingPools2f = [new SwimmingPool(1, swimmingPool1), new SwimmingPool(2, swimmingPool1)]
            swimmingPoolRepository.findAllByDepth(2f) >> allSwimmingPools2f
            swimmingPoolService = new SwimmingPoolService(swimmingPoolRepository)
        when:
            Set<SwimmingPool> returnedSwimmingPoolsSet = swimmingPoolService.getAllSwimmingPoolsByDepth(2f)
        then:
            returnedSwimmingPoolsSet == allSwimmingPools2f
    }

    def "should return all swimming pools with given lanes quantity"(){
        given:
            Set<SwimmingPool> allSwimmingPools5Lanes = [new SwimmingPool(1, swimmingPool1), new SwimmingPool(2, swimmingPool1)]
            swimmingPoolRepository.findAllByLanesQuantity(5) >> allSwimmingPools5Lanes
            swimmingPoolService = new SwimmingPoolService(swimmingPoolRepository)
        when:
            Set<SwimmingPool> returnedSwimmingPoolsSet = swimmingPoolService.getAllSwimmingPoolsByLanesQuantity(5)
        then:
            returnedSwimmingPoolsSet == allSwimmingPools5Lanes
    }

    def "should return edited swimming pools"(){
        given:
            SwimmingPool editedSwimmingPool = new SwimmingPool(1, swimmingPool1)
            swimmingPoolRepository.save(editedSwimmingPool) >> editedSwimmingPool
            swimmingPoolService = new SwimmingPoolService(swimmingPoolRepository)
        when:
            SwimmingPool returnedSwimmingPool = swimmingPoolService.editSwimmingPool(editedSwimmingPool,1)
        then:
            returnedSwimmingPool.getId() == 1
    }

}
