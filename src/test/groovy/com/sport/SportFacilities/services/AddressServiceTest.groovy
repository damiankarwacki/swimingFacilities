package com.sport.SportFacilities.services

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.repositories.AddressRepository
import spock.lang.Shared
import spock.lang.Specification

class AddressServiceTest extends Specification {
    
    @Shared AddressRepository addressRepository
    @Shared Address address

    def setup(){
        addressRepository = Mock()
        address = new Address("street","city","postCode")
    }
    
    def "Create address method should return address with newly created id"() {
        given:
            addressRepository.save(address) >> new Address(1,address)
            AddressService addressService = new AddressService(addressRepository)
        when:
            Address resultAddress = addressService.createAddress(address)
        then:
            resultAddress.getId() == 1
    }

    def "Edit address method should return the same address that is provided"() {
        given:
            Address editedAddress = new Address(1,address)
            addressRepository.save(editedAddress) >> editedAddress
            AddressService addressService = new AddressService(addressRepository)
        when:
            Address resultAddress = addressService.editAddress(editedAddress)
        then:
            resultAddress.equals(editedAddress)
    }

    def "Get by id method should find address with given id"() {
        given:
            addressRepository.findById(1) >> Optional.of(new Address(1,address))
            addressRepository.findById(2) >> Optional.of(new Address(2, address))
            AddressService addressService = new AddressService(addressRepository)
        when:
            Address address1 = addressService.getAddressById(1)
            Address address2 = addressService.getAddressById(2)
        then:
            address1.getId() == 1
            address2.getId() == 2
    }
    
    def "Get by id method should throw an exception if address with given id cannot be found"() {
        given:
            addressRepository.findById(1) >> Optional.ofNullable(null)
            AddressService addressService = new AddressService(addressRepository)
        when:
            addressService.getAddressById(1)
        then:
            thrown(NoSuchElementException)
    }

    def "Get all Addresses should return all addresses from database"() {
        given:
            Set<Address> givenAddresses = [new Address(1,address), new Address(2,address)]
            addressRepository.findAll() >> givenAddresses
            AddressService addressService = new AddressService(addressRepository)
        when:
            Set<Address> resultAddresses = addressService.getAllAddresses()
        then:
            resultAddresses == givenAddresses
    }
}
