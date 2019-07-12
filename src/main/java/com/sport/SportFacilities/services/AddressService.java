package com.sport.SportFacilities.services;

import com.google.common.collect.Sets;
import com.sport.SportFacilities.models.Address;
import com.sport.SportFacilities.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AddressService {
    
    private AddressRepository addressRepository;
    
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    
    public Address createAddress(Address address){
        return addressRepository.save(address);
    }
    
    public void deleteAddress(Address address){
        addressRepository.delete(address);
    }
    
    public Address editAddress(Address address){
        return addressRepository.save(address);
    }
    
    public Address getAddressById(Integer id){
        return addressRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Set<Address> getAllAddresses(){
        return Sets.newHashSet(addressRepository.findAll());
    }
}
