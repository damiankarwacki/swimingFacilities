package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.exceptions.CustomerNotFoundException;
import com.sport.SportFacilities.models.Customer;
import com.sport.SportFacilities.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CustomerService {
    
    private CustomerRepository customerRepository;
    
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    public Customer createCustomer(Customer Customer){
        return customerRepository.save(Customer);
    }
    
    public void deleteCustomer(Customer Customer){
        customerRepository.delete(Customer);
    }
    
    public Customer editCustomer(Customer customer, Integer id){
        Customer editedCustomer = getCustomerById(id);
        editedCustomer.setSurname(customer.getSurname());
        editedCustomer.setPhone(customer.getPhone());
        editedCustomer.setEmail(customer.getEmail());
        editedCustomer.setName(customer.getName());
        return editedCustomer;
    }
    
    public Customer getCustomerById(Integer id){
        return customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("id", String.valueOf(id)));
    }
    
    public Set<Customer> getAllCustomers(){
        return Sets.newHashSet(customerRepository.findAll());
    }
}
