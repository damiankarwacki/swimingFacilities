package com.sport.SportFacilities.controllers

import spock.lang.Specification
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import com.sport.SportFacilities.exceptions.SportObjectNotFoundException
import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.services.SportObjectService
import com.sport.SportFacilities.utils.HateoasUtils
import org.springframework.hateoas.Link
import org.springframework.http.ResponseEntity
import spock.lang.Shared
class SportObjectControllerTest extends Specification {

    @Shared
    SportObjectService sportObjectService
    @Shared
    HateoasUtils hateoasUtils
    @Shared
    Set<SportObject> sportObjectsInDb
    @Shared
    SportObject sportObjectToAdd
    @Shared
    SportObject sportObjectWithId1
    @Shared
    SportObject sportObjectInCity2

    def setup() {
        hateoasUtils = Mock()
        sportObjectService = Mock()
        sportObjectToAdd = new SportObject("New Sport Object", new Address("street", "city", "postCode"))
        sportObjectInCity2 = new SportObject("Sport Object 2", new Address("street2", "city2", "postCode2"))
        sportObjectWithId1 = new SportObject("Sport Object 1", new Address("street1", "city1", "postCode1"))
        sportObjectsInDb = [sportObjectWithId1,
                            sportObjectInCity2,
                            new SportObject("Sport Object 3", new Address("street3", "city3", "postCode3"))]
    }

    def "Check if getAllSportObject method return all Sport Objects"() {
        given:
            sportObjectService.getAllSportObjects() >> sportObjectsInDb
            SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
            ResponseEntity response = sportObjectController.getAllSportObjects()
        then:
            response == ResponseEntity.ok(sportObjectsInDb)
    }

    def "Check if getAllSportObjectFromCity method return proper sportObject"() {
        given:
            sportObjectService.getAllSportObjectsByCity("city2") >> [sportObjectInCity2]
            SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
            ResponseEntity response = sportObjectController.getAllSportObjectFromCity("city2")
        then:
            response.getBody()[0] == sportObjectInCity2
    }

    def "Getting request for sport objects in not existing city should throw exception"() {
        given:
            sportObjectService.getAllSportObjectsByCity("no existing city") >> {throw new SportObjectNotFoundException()}
            SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
            sportObjectController.getAllSportObjectFromCity("no existing city")
        then:
            thrown(SportObjectNotFoundException)
    }

    def "Check if getSportObjectById method return response with proper content and links"() {
        given:
            Link linkForGettingAllSportObjects = linkTo(methodOn(SportObjectController.class).getAllSportObjects())
                .withRel("all-sport-objects")
            sportObjectService.getSportObjectById(1) >> sportObjectWithId1
            SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
            ResponseEntity response = sportObjectController.getSportObjectById(1)
        then:
            response.getBody()["content"] == sportObjectWithId1
            response.getBody()["links"][0] == linkForGettingAllSportObjects
    }

    def "Getting request for sport object with is not exists should throw exception"() {
        given:
            sportObjectService.getSportObjectById(2) >> {throw new SportObjectNotFoundException()}
            SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
            sportObjectController.getSportObjectById(2)
        then:
            thrown(SportObjectNotFoundException)
    }

    def "Check if creation of sport object return proper response with uri"() {
        given:
            hateoasUtils.getUriWithPathAndParams(_,_) >> URI.create("uri")
            sportObjectService.createNewSportObject(sportObjectToAdd) >> sportObjectToAdd
            SportObjectController sportObjectController = new SportObjectController(sportObjectService,hateoasUtils)
        when:
            ResponseEntity response = sportObjectController.createSportObject(sportObjectToAdd)
        then:
            response == ResponseEntity.created(URI.create("uri")).body(sportObjectToAdd)
    }

    def "Check if editSportObject method calls proper service method"() {
        given:
            SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
            sportObjectController.editSportObject(sportObjectWithId1)
        then:
            1 * sportObjectService.editSportObject(sportObjectWithId1)
    }

    def "Check if deleteSportObject method calls proper service method"() {
        given:
            SportObjectController sportObjectController = new SportObjectController(sportObjectService)
        when:
            sportObjectController.deleteSportObject(1)
        then:
            1 * sportObjectService.getSportObjectById(1) >> sportObjectWithId1
            1 * sportObjectService.deleteSportObject(sportObjectWithId1)
    }
}