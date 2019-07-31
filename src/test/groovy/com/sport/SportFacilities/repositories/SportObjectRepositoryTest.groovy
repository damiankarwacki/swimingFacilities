package com.sport.SportFacilities.repositories

import com.sport.SportFacilities.controllers.SportObjectController
import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.PendingFeature
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

import javax.validation.ConstraintViolationException
import java.sql.SQLException

@SpringBootTest
@Stepwise
class SportObjectRepositoryTest extends Specification {
    
    @Autowired
    SportObjectRepository sportObjectRepository
    
    @Shared
    Address address
    
    @Shared
    SportObject sportObject
    
    def setup(){
        address = new Address("Street","City","99-434")
        sportObject = new SportObject("SportObject",address)
    }
    
    def "Check if database is up"(){
        when:
            sportObjectRepository.existsById(1)
        then:
            notThrown(SQLException)
        
    }
    
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
            Address newAddress = new Address("NewStreet","NewCity","99-454")
        when:
            sportObjectRepository.save(sportObject)
            sportObject.setAddress(newAddress)
            SportObject sportObjectFromDb = sportObjectRepository.findById(sportObject.getId()).orElse(null)
        then:
            sportObjectFromDb == sportObject
    }
    
    
    @Transactional
    def "check if return all sport object in given city"() {
        given:
            Address addressInCity1 = new Address("Street 1","City","99-400")
            Address addressInCity2 = new Address("Street 2","Citytwo","99-401")
            SportObject sportObjectInCity1 = new SportObject("SportObject",addressInCity1)
            SportObject sportObjectInCity2 = new SportObject("SportObject",addressInCity2)
            Set<SportObject> sportObjects = [sportObjectInCity1, sportObjectInCity2]
            sportObjects.stream().forEach {s -> sportObjectRepository.save(s)}
        when:
            Set<SportObject> sportObjectsFromDb = sportObjectRepository.findAllByCity("City").orElse(Collections.emptySet())
        then:
            sportObjectsFromDb.forEach({ c -> c.getAddress().getCity() == "City" })
    }
}
