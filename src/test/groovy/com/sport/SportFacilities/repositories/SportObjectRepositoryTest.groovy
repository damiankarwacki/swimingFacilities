package com.sport.SportFacilities.repositories

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class SportObjectRepositoryTest extends Specification {
    
    @Autowired
    SportObjectRepository sportObjectRepository
    
    @Shared
    Address address = new Address("Street","City","PostCode")
    
    @Shared
    SportObject sportObject = new SportObject(address,"SportObject")
    
    
    def "check if sportObject is saved in DB"() {
        when:
            SportObject savedSportObject = sportObjectRepository.save(sportObject)
        then:
            savedSportObject == sportObject
    }
    
    //Tip: jeśli wewnątrz jednej metody pojawia się wiele interakcji z bazą danych to są one wykonane w jednej transakcji bazodanowej
    //Tip2: @Transactional - dane z adnotacjami FetchType.LAZY są poprawnie ściągane, przeciwnie hibernete nie może się zorientować, że odwołaliśmy się do danego parametru
    //      najwyraźniej leniwe ściąganie danych musi być wykonywane w ramach tej samej transakcji bazodanowej
    
    @Transactional
    def "check if saved object will change id when setter called"() {
        given:
            Address newAddress = new Address("NewStreet","NewCity","NewPostCode")
        when:
            sportObjectRepository.save(sportObject)
            sportObject.setAddress(newAddress)
            SportObject sportObjectFromDb = sportObjectRepository.findById(sportObject.getId()).orElse(null)
        then:
            sportObjectFromDb == sportObject
    }
    
    
    @Transactional
    def "should return all sport object in respective city"() {
        given:
            Address addressInTheSameCity = new Address("Street2","City","PostCode2")
            Set<SportObject> sportObjects = [
                    new SportObject(address,"1"),
                    new SportObject(addressInTheSameCity,"2")
            ]
        
        when:
            sportObjects.stream().forEach {s -> sportObjectRepository.save(s)}
            Set<SportObject> sportObjectsFromDb = sportObjectRepository.findAllByCity("City").orElse(Collections.emptySet())
        then:
            sportObjectsFromDb == sportObjects
    }
}
