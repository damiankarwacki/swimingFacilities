package com.sport.SportFacilities.controllers;


import com.sport.SportFacilities.models.Address;
import com.sport.SportFacilities.services.AddressService;
import com.sport.SportFacilities.utils.HateoasHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Set;

//Kontroler nie potrzebny, zarządzanie adresami powinno odbywać się wewnątrz kontrollera SportObject
//Mimo to nie usunięty bo może pojawi się koncepcja gdzie ponownie wykorzystamy te adresy
@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping()
    public ResponseEntity getAllAddresses(){
        Set<Address> addresses = addressService.getAllAddresses();
        ResponseEntity responseEntity = addresses.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(addresses);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity getAddressById(@PathVariable Integer id){
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping()
    public ResponseEntity createAddress(@RequestBody Address address){
            Address createdAddress = addressService.createAddress(address);
            URI uri = HateoasHelper
                    .getUriWithPathAndParams("/{id}", createdAddress.getId());
            return ResponseEntity.created(uri).body(createdAddress);
    }

    @PutMapping()
    public ResponseEntity editAddress(@RequestBody Address address){
        Address updatedAddress = addressService.editAddress(address);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAddress(@PathVariable Integer id){
        Address address = addressService.getAddressById(id);
        addressService.deleteAddress(address);
        return ResponseEntity.ok().build();
    }

}

