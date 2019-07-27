package groovy.com.sport.SportFacilities.services

import com.sport.SportFacilities.models.Customer
import com.sport.SportFacilities.repositories.CustomerRepository
import com.sport.SportFacilities.services.CustomerService
import spock.lang.Shared
import spock.lang.Specification

class CustomerServiceTest extends Specification {
    
    @Shared CustomerRepository customerRepository
    @Shared Customer customer
    
    def setup(){
        customerRepository = Mock()
        customer = new Customer("name","surname","phone","email")
    }
    
    def "Create customer method should return customer with newly created id"() {
        given:
            customerRepository.save(customer) >> new Customer(1,customer)
            CustomerService CustomerService = new CustomerService(customerRepository)
        when:
            Customer resultCustomer = CustomerService.createCustomer(customer)
        then:
            resultCustomer.getId() == 1
    }
    
    def "Edit customer method should return the same customer that is provided"() {
        given:
            Customer editedCustomer = new Customer(1,customer)
            customerRepository.save(editedCustomer) >> editedCustomer
            CustomerService customerService = new CustomerService(customerRepository)
        when:
            Customer resultCustomer = customerService.editCustomer(editedCustomer, 1)
        then:
            resultCustomer.equals(editedCustomer)
    }
    
    def "Get by id method should find customer with given id"() {
        given:
            customerRepository.findById(1) >> Optional.of(new Customer(1,customer))
            customerRepository.findById(2) >> Optional.of(new Customer(2, customer))
            CustomerService customerService = new CustomerService(customerRepository)
        when:
            Customer customer1 = customerService.getCustomerById(1)
            Customer customer2 = customerService.getCustomerById(2)
        then:
            customer1.getId() == 1
            customer2.getId() == 2
    }
    
    def "Get by id method should throw an exception if Customer with given id cannot be found"() {
        given:
            customerRepository.findById(1) >> Optional.ofNullable(null)
            CustomerService CustomerService = new CustomerService(customerRepository)
        when:
            CustomerService.getCustomerById(1)
        then:
            thrown(NoSuchElementException)
    }
    
    def "Get all customeres should return all customeres from database"() {
        given:
            Set<Customer> givenCustomers = [new Customer(1,customer), new Customer(2,customer)]
            customerRepository.findAll() >> givenCustomers
            CustomerService customerService = new CustomerService(customerRepository)
        when:
            Set<Customer> resultCustomers = customerService.getAllCustomers()
        then:
            resultCustomers == givenCustomers
    }
}
