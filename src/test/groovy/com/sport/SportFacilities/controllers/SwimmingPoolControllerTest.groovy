package groovy.com.sport.SportFacilities.controllers

import com.sport.SportFacilities.controllers.SportObjectController
import com.sport.SportFacilities.controllers.SwimmingPoolController
import com.sport.SportFacilities.exceptions.SwimmingPoolNotFoundException
import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.models.SwimmingPool
import com.sport.SportFacilities.services.SwimmingPoolService
import com.sport.SportFacilities.utils.HateoasUtils
import org.springframework.hateoas.Link
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class SwimmingPoolControllerTest extends Specification {
    @Shared
    SwimmingPoolService swimmingPoolService
    @Shared
    Set<SwimmingPool> swimmingPoolsFromDb
    @Shared
    HateoasUtils hateoasUtils
    @Shared
    SwimmingPool swimmingPoolToAdd
    @Shared
    SportObject sportObject
    @Shared
    URI uri
    @Shared
    SwimmingPool swimmingPool
    @Shared
    SwimmingPool swimmingPool1
    @Shared
    SwimmingPool swimmingPool2
    @Shared
    SwimmingPool swimmingPoolWithId1

    def setup(){
        hateoasUtils = Mock()
        swimmingPoolService = Mock()
        sportObject = new SportObject("name", new Address("street", "city", "code"))
        swimmingPoolToAdd = new SwimmingPool(5, 25f, 2f, sportObject)
        swimmingPool = new SwimmingPool(6, 25f, 2f, sportObject)
        swimmingPool1 = new SwimmingPool(6, 50f, 2f, sportObject)
        swimmingPool2 = new SwimmingPool(6, 50f, 2f, sportObject)
        swimmingPoolsFromDb = [swimmingPool, swimmingPool1, swimmingPool2]

        swimmingPoolWithId1 = new SwimmingPool(1, swimmingPool)
        uri = URI.create("uri")
    }

    def "should return created swimming pool with a proper uri"(){
        given:
            hateoasUtils.getUriWithPathAndParams(_,_) >> uri
            swimmingPoolService.createSwimmingPool(swimmingPoolToAdd) >> swimmingPoolToAdd
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService,hateoasUtils)
        when:
            ResponseEntity response = swimmingPoolController.createSwimmingPool(swimmingPoolToAdd)
        then:
            response == ResponseEntity.created(uri).body(swimmingPoolToAdd)
    }

    def "should return swimming pool with given id"(){
        given:
            Link linkToAllInstructors = linkTo(methodOn(SwimmingPoolController.class).getAllSwimmingPools()).withRel("all-swimming-pools");
            swimmingPoolService.getSwimmingPoolById(1) >> swimmingPoolWithId1
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService)
        when:
            ResponseEntity response = swimmingPoolController.getSwimmingPoolById(1)
        then:
            response.getBody()["content"] == swimmingPoolWithId1
            response.getBody()["links"][0] == linkToAllInstructors
    }

    def "should return exception of no such swimming pool"(){
        given:
            swimmingPoolService.getSwimmingPoolById(1) >> {throw new SwimmingPoolNotFoundException()}
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService)
        when:
            swimmingPoolController.getSwimmingPoolById(1)
        then:
            thrown(SwimmingPoolNotFoundException)
    }

    def "should return all swimming pools from db"(){
        given:
            swimmingPoolService.getAllSwimmingPools() >> swimmingPoolsFromDb
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService)
        when:
            ResponseEntity res = swimmingPoolController.getAllSwimmingPools()
        then:
            res == ResponseEntity.ok(swimmingPoolsFromDb)
    }

    def "should return swimming pools with depth 2"(){
        given:
            swimmingPoolService.getAllSwimmingPoolsByDepth(2f) >> swimmingPoolsFromDb
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService)
        when:
            ResponseEntity res = swimmingPoolController.getSwimmingPoolsByDepth(2)
        then:
            res == ResponseEntity.ok(swimmingPoolsFromDb)
    }

    def "should return swimming pools with 6 lanes"(){
        given:
            swimmingPoolService.getAllSwimmingPoolsByLanesQuantity(6)>>swimmingPoolsFromDb
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService)
        when:
            ResponseEntity res = swimmingPoolController.getSwimmingPoolsByLanesQuantity(6)
        then:
            res == ResponseEntity.ok(swimmingPoolsFromDb)
    }

    def "should return edited swimming pool"(){
        given:
            swimmingPoolService.editSwimmingPool(swimmingPoolWithId1, 1) >> swimmingPoolWithId1
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService)
        when:
            ResponseEntity res = swimmingPoolController.editSwimmingPool(swimmingPoolWithId1, 1)
        then:
            res == ResponseEntity.ok(swimmingPoolWithId1)
    }

    def "check if delete method call a proper method"(){
        given:
            SwimmingPoolController swimmingPoolController = new SwimmingPoolController(swimmingPoolService)
        when:
            swimmingPoolController.deleteSwimmingPool(1)
        then:
            1 * swimmingPoolService.getSwimmingPoolById(1) >> swimmingPoolWithId1
            1 * swimmingPoolService.deleteSwimmingPool(swimmingPoolWithId1)
    }


}
