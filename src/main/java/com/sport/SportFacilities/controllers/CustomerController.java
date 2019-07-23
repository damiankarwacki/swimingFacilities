package com.sport.SportFacilities.controllers;

import com.sport.SportFacilities.models.Customer;
import com.sport.SportFacilities.services.CustomerService;
import com.sport.SportFacilities.utils.HateoasUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private HateoasUtils hateoasUtils;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.hateoasUtils = new HateoasUtils();
    }

    @PostMapping()
    public ResponseEntity createCustomer(@RequestBody Customer customer){
        Customer createdCustomer = customerService.createCustomer(customer);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}", createdCustomer.getId());
        return ResponseEntity.created(uri).body(createdCustomer);
    }

    @GetMapping()
    public ResponseEntity getAllCustomers(){
        Set<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCustomerById(@PathVariable("id") Integer id){
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping()
    public ResponseEntity editCustomer(@RequestBody Customer customer){
        Customer editedCustomer = customerService.editCustomer(customer);
        URI uri = hateoasUtils.getUriWithPathAndParams("/{id}",editedCustomer.getId());
        return ResponseEntity.created(uri).body(editedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") Integer id){
        Customer customerToDelete = customerService.getCustomerById(id);
        customerService.deleteCustomer(customerToDelete);
        return ResponseEntity.ok().build();
    }

}
