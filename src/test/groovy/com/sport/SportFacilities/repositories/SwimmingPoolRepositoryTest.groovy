package com.sport.SportFacilities.repositories

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.models.SwimmingPool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import org.assertj.core.util.Sets
import spock.lang.Specification
import spock.lang.Stepwise

import java.sql.SQLException

@SpringBootTest
@Stepwise
//Stepwise zapewnia, że testy będą wykonywane w kolejności i jeśli jeden nie przejdzie to dalsze też nie
class SwimmingPoolRepositoryTest extends Specification {
    
    SportObject sportObject = new SportObject("Object",new Address())
    
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
    
    def "Check if database is up"(){
        when:
            swimmingPoolRepository.existsById(1)
        then:
            notThrown(SQLException)
        
    }
    
    @Transactional
    def "should return swimming pools with given lanes quantity"() {
        given:
            swimmingPools.stream().forEach { s -> swimmingPoolRepository.save(s)}
        when:
            Set<SwimmingPool> swimmingPoolsDb = swimmingPoolRepository.findAllByLanesQuantity(4).orElse(Collections.emptySet())
        then:
            swimmingPoolsDb == Sets.newLinkedHashSet(swimmingPoolLanes4)
            
    }

    @Transactional
    def "should return swimming pools with given depth"() {
        given:
            swimmingPools.stream().forEach { s -> swimmingPoolRepository.save(s)}
        when:
            Set<SwimmingPool> swimmingPoolsDb = swimmingPoolRepository.findAllByDepth(2).orElse(Collections.emptySet())
        then:
            swimmingPoolsDb == Sets.newLinkedHashSet(swimmingPoolDepth2)
    }
}
