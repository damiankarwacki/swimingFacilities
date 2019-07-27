package com.sport.SportFacilities.controllers;

import com.sport.SportFacilities.models.Customer;
import com.sport.SportFacilities.services.CustomerService;
import com.sport.SportFacilities.utils.HateoasUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
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
        Link linkToAllCustomers = linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers");
        Customer customer = customerService.getCustomerById(id);
        Resource<Customer> resource = new Resource<>(customer);
        resource.add(linkToAllCustomers);
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity editCustomer(@RequestBody Customer customer, @PathVariable Integer id){
        Customer editedCustomer = customerService.editCustomer(customer, id);
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
