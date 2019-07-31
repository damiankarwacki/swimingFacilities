package com.sport.SportFacilities.validations

import com.sport.SportFacilities.models.Address
import com.sport.SportFacilities.models.SportObject
import com.sport.SportFacilities.repositories.SportObjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

@SpringBootTest
class SportObjectAndAddressValidationTest extends Specification {

    @Autowired
    SportObjectRepository sportObjectRepository

    @Unroll
    def "Validation of #correctVariable == #correctValue should not throw any exceptions"(){
        given:
        SportObject sportObject = new SportObject(sportObjectName,new Address(street, city, postCode))
        when:
        sportObjectRepository.save(sportObject)
        then:
        notThrown(ConstraintViolationException)
        where:
        sportObjectName |street          | city             | postCode     | correctVariable         | correctValue
        "SportObject"   |"Street"        | "City"           | "99-434"     | "postCode"            | postCode
        "SportObject"   |"Street"        | "City"           | "11-111"     | "postCode"            | postCode
        "SportObject"   |"1"             | "City"           | "99-434"     | "street"              | street
        "SportObject"   |"S"             | "City"           | "99-434"     | "street"              | street
        "SportObject"   |"S1"            | "City"           | "99-434"     | "street"              | street
        "SportObject"   |"Street"        | "City"           | "99-434"     | "street"              | street
        "SportObject"   |"Street 2"      | "City"           | "99-434"     | "street"              | street
        "SportObject"   |"Street Street" | "City"           | "99-434"     | "street"              | street
        "SportObject"   |"212 Street"    | "City"           | "99-434"     | "street"              | street
        "SportObject"   |"Street"        | "Ci"             | "99-434"     | "city"                | city
        "SportObject"   |"Street"        | "City"           | "99-434"     | "city"                | city
        "SportObject"   |"Street"        | "City City"      | "99-434"     | "city"                | city
        "SportObject"   |"Street"        | "City City City" | "99-434"     | "city"                | city
        "1"             |"Street"        | "City"           | "99-434"     | "sportObjectName"     | sportObjectName
        "S"             |"Street"        | "City"           | "99-434"     | "sportObjectName"     | sportObjectName
        "S1"            |"Street"        | "City"           | "99-434"     | "sportObjectName"     | sportObjectName
        "SportObject"   |"Street"        | "City"           | "99-434"     | "sportObjectName"     | sportObjectName
        "SportObject 2" |"Street"        | "City"           | "99-434"     | "sportObjectName"     | sportObjectName
        "Sport Sport"   |"Street"        | "City"           | "99-434"     | "sportObjectName"     | sportObjectName
        "212 Sport"     |"Street"        | "City"           | "99-434"     | "sportObjectName"     | sportObjectName
    }

    @Unroll
    def "Validation of #wrongVariable == #wrongValue should throw ConstraintViolationException"() {
        given:
        SportObject sportObject = new SportObject(sportObjectName, new Address(street, city, postCode))
        when:
        sportObjectRepository.save(sportObject)
        then:
        thrown(ConstraintViolationException)
        where:
        sportObjectName | street   | city       | postCode   | wrongVariable     | wrongValue
        "SportObject"   | "Street" | "City"     | "994343"   | "postCode"        | postCode
        "SportObject"   | "Street" | "City"     | "994--434" | "postCode"        | postCode
        "SportObject"   | "Street" | "City"     | "99 434"   | "postCode"        | postCode
        "SportObject"   | "Street" | "City"     | "99434"    | "postCode"        | postCode
        "SportObject"   | ""       | "City"     | "99-434"   | "street"          | street
        "SportObject"   | "s"      | "City"     | "99-434"   | "street"          | street
        "SportObject"   | "s1"     | "City"     | "99-434"   | "street"          | street
        "SportObject"   | "street" | "City"     | "99-434"   | "street"          | street
        "SportObject"   | "Street" | "123"      | "99-434"   | "city"            | city
        "SportObject"   | "Street" | ""         | "99-434"   | "city"            | city
        "SportObject"   | "Street" | "City city"| "99-434"   | "city"            | city
        "SportObject"   | "Street" | "C"        | "99-434"   | "city"            | city
        "SportObject"   | "Street" | "C1"       | "99-434"   | "city"            | city
        "SportObject"   | "Street" | "City 1"   | "99-434"   | "city"            | city
        "SportObject"   | "Street" | "1 City"   | "99-434"   | "city"            | city
        "SportObject"   | "Street" | "1City"    | "99-434"   | "city"            | city
        ""              | "Street" | "City"     | "99-434"   | "sportObjectName" | sportObjectName
        "s"             | "Street" | "City"     | "99-434"   | "sportObjectName" | sportObjectName
        "s1"            | "Street" | "City"     | "99-434"   | "sportObjectName" | sportObjectName
        "sportObject"   | "Street" | "City"     | "99-434"   | "sportObjectName" | sportObjectName
    }
}