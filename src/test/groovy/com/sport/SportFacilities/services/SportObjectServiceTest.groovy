package com.sport.SportFacilities.services

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.repositories.SportObjectRepository
import com.sport.SportFacilities.services.SportObjectService
import spock.lang.Shared
import spock.lang.Specification

class SportObjectServiceTest extends Specification {

    @Shared SportObjectRepository sportObjectRepository
    @Shared SportObjectService sportObjectService
    @Shared SportObject sportObject1
    @Shared SportObject sportObject2
    @Shared Address address1
    @Shared Address address2

    def setup(){
        sportObjectRepository = Mock()
        address1 = new Address("street1", "city1", "postcode1")
        address2 = new Address("street2", "city2", "postcode2")
        sportObject1 = new SportObject("name1", address1)
        sportObject2 = new SportObject("name2", address2)
    }

    def "should return created sport object with id"(){
        given:
            sportObjectRepository.save(sportObject1) >> new SportObject(1, sportObject1)
            sportObjectService = new SportObjectService(sportObjectRepository)
        when:
            SportObject returnedSportObject = sportObjectService.createNewSportObject(sportObject1)
        then:
            returnedSportObject.getId() == 1
    }

    def "should return sport object with given id"(){
        given:
            sportObjectRepository.findById(1) >> Optional.of(new SportObject(1, sportObject1))
            sportObjectService = new SportObjectService(sportObjectRepository)
        when:
            SportObject returnedSportObject = sportObjectService.getSportObjectById(1)
        then:
            returnedSportObject.getId() == 1
    }

    def "should return all sport objects"(){
        given:
            Set<SportObject> allSportObjects = [new SportObject(1, sportObject1), new SportObject(2, sportObject2)]
            sportObjectRepository.findAll() >> allSportObjects
            sportObjectService = new SportObjectService(sportObjectRepository)
        when:
            Set<SportObject> returnedSportObjectsSet = sportObjectService.getAllSportObjects()
        then:
            returnedSportObjectsSet == allSportObjects
    }

    def "should return all sport objects in given city"(){
        given:
            Set<SportObject> allSportObjectsInCity1 = [new SportObject(1, sportObject1), new SportObject(2, sportObject1)]
            sportObjectRepository.findAllByCity("city1") >> allSportObjectsInCity1
            sportObjectService = new SportObjectService(sportObjectRepository)
        when:
            Set<SportObject> returnedSportObjectsSet = sportObjectService.getAllSportObjectsByCity("city1")
        then:
            returnedSportObjectsSet == allSportObjectsInCity1
    }

    def "should return edited sport object"(){
        given:
            SportObject beforeEdition = new SportObject(1,sportObject1)
            SportObject afterEdition = new SportObject(1, sportObject2)
            sportObjectRepository.findById(1) >> Optional.of(beforeEdition)
            sportObjectService = new SportObjectService(sportObjectRepository)
        when:
            SportObject returnedSportObject = sportObjectService.editSportObject(1,afterEdition)
        then:
            returnedSportObject == afterEdition
    }

}
