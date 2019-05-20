package com.sport.SportFacilities.repositories

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.models.SwimmingPool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class SwimmingPoolRepositoryTest extends Specification {
    
    SportObject sportObject = new SportObject(new Address(),"Object")
    
    @Autowired
    SwimmingPoolRepository swimmingPoolRepository
    @Shared
    SwimmingPool swimmingPoolDepth2
    @Shared
    SwimmingPool swimmingPoolLanes4
    @Shared
    Set<SwimmingPool> swimmingPools
    
    @Transactional
    def setup(){
        swimmingPoolDepth2 = new SwimmingPool(2,50,2, sportObject)
        swimmingPoolLanes4 = new SwimmingPool(4,50,1.8, sportObject)
        swimmingPools = [swimmingPoolDepth2, swimmingPoolLanes4]
    }
    
    def cleanup(){
        swimmingPools = Collections.emptySet()
    }
    
    @Transactional
    def "should return swimming pools with given lanes quantity"() {
        given:
            swimmingPools.stream().forEach { s -> swimmingPoolRepository.save(s)}
        when:
            Set<SwimmingPool> swimmingPoolsDb = swimmingPoolRepository.findAllByLanesQuantity(4).orElse(Collections.emptySet())
        then:
            swimmingPoolsDb[0] == swimmingPoolLanes4
            
    }

    @Transactional
    def "should return swimming pools with given depth"() {
        given:
            swimmingPools.stream().forEach { s -> swimmingPoolRepository.save(s)}
        when:
            Set<SwimmingPool> swimmingPoolsDb = swimmingPoolRepository.findAllByDepth(2).orElse(Collections.emptySet())
        then:
            swimmingPoolsDb[0] == swimmingPoolDepth2
    }
}
