package groovy.com.sport.SportFacilities.controllers

import com.sport.SportFacilities.controllers.CustomerController
import com.sport.SportFacilities.exceptions.CustomerNotFoundException
import com.sport.SportFacilities.models.Customer
import com.sport.SportFacilities.services.CustomerService
import com.sport.SportFacilities.utils.HateoasUtils
import org.springframework.hateoas.Link
import org.springframework.http.ResponseEntity
import spock.lang.Shared
import spock.lang.Specification
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn


class CustomerControllerTest extends Specification {

    @Shared HateoasUtils hateoasUtils
    @Shared CustomerService customerService
    @Shared Customer customer1
    @Shared Customer customer2
    @Shared Customer customer3
    @Shared Customer customer1WithId
    @Shared URI uri
    @Shared Set<Customer> customers

    def setup(){
        hateoasUtils = Mock()
        customerService = Mock()

        customer1 = new Customer("name1", "surname1", "phone1", "email@gmail.com")
        customer1WithId = new Customer(1, customer1)
        customer2 = new Customer("name2", "surname2", "phone2", "email@gmail.com")
        customer3 = new Customer("name3", "surname3", "phone3", "email@gmail.com")

        customers = [customer1, customer2, customer3]

    }

    def "should return created customer with a proper uri"(){
        given:
            hateoasUtils.getUriWithPathAndParams(_,_) >> uri
            customerService.createCustomer(customer1) >> customer1WithId
            CustomerController customerController = new CustomerController(customerService, hateoasUtils)
        when:
            ResponseEntity res = customerController.createCustomer(customer1)
        then:
            res == ResponseEntity.created(uri).body(customer1WithId)
    }

    def "should return customer with given id"(){
        given:
            Link link = linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers")
            customerService.getCustomerById(1) >> customer1WithId
            CustomerController customerController = new CustomerController(customerService)
        when:
            ResponseEntity res = customerController.getCustomerById(1)
        then:
            res.getBody()["content"] == customer1WithId
            res.getBody()["links"][0] == link
    }

    def "should return exception"(){
        given:
            customerService.getCustomerById(1) >> {throw new CustomerNotFoundException()}
            CustomerController customerController = new CustomerController(customerService)
        when:
            customerController.getCustomerById(1)
        then:
            thrown(CustomerNotFoundException)
    }

    def "should return all customers"(){
        given:
            customerService.getAllCustomers() >> customers
            CustomerController customerController = new CustomerController(customerService)
        when:
            ResponseEntity res = customerController.getAllCustomers()
        then:
            res == ResponseEntity.ok(customers)
    }

    def "should return edited customer with proper uri"(){
        given:
            hateoasUtils.getUriWithPathAndParams(_,_) >> uri
            customerService.editCustomer(customer1, 1) >> customer1
            CustomerController customerController = new CustomerController(customerService, hateoasUtils)
        when:
            ResponseEntity res = customerController.editCustomer(customer1, 1)
        then:
            res == ResponseEntity.created(uri).body(customer1)
    }

    def "check if runs proper methods when deleting"(){
        given:
            CustomerController customerController = new CustomerController(customerService)
        when:
            customerController.deleteCustomer(1)
        then:
            1 * customerService.getCustomerById(1) >> customer1WithId
            1 * customerService.deleteCustomer(customer1WithId)
    }
}
