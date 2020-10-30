package com.kinsella.people.unit.business.service;

import com.kinsella.people.business.service.AddressService;
import com.kinsella.people.business.service.PersonService;
import com.kinsella.people.business.service.impl.AddressServiceImpl;
import com.kinsella.people.business.service.impl.PersonServiceImpl;
import com.kinsella.people.data.entity.Address;
import com.kinsella.people.data.entity.Person;
import com.kinsella.people.data.repository.AddressRepository;
import com.kinsella.people.data.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AddressServiceTest {
    @TestConfiguration
    static class AddressServiceTestContextConfiguration {
        @Bean
        public AddressService addressService() {
            return new AddressServiceImpl();
        }
        @Bean
        public PersonService personService() {
            return new PersonServiceImpl();
        }
    }

    @Autowired
    private AddressService addressService;
    @Autowired
    private PersonService personService;

    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private PersonRepository personRepository;

    @Before
    public void setUp() {
        Person testPerson = new Person(1,"Test", "Person");
        Address testAddress = new Address(1,testPerson,"Test", "Test", "Test","Test");
        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.of(testPerson));

        Mockito.when(addressRepository.findById(1L))
                .thenReturn(Optional.of(testAddress));
        Mockito.when(addressRepository.existsById(1L))
                .thenReturn(true);

        Address testAddressUpdated = new Address(1,testPerson,"Updated", "Test", "Test","Test");
        Mockito.when(addressRepository.save(testAddressUpdated))
                .thenReturn(testAddressUpdated);

    }
    @Test
    public void testAddressReturnsAddressById() {
        Optional<Address> testInDatabase = addressService.get(1);
        assertThat(testInDatabase.isPresent()).isEqualTo(true);
    }

    @Test
    public void testCreateDoesNotAllowDupes() {
        Address dupeIdAddress = new Address(1,new Person(1,"2","3"),"","","","");
        Optional<Address> shouldBeEmpty = addressService.create(dupeIdAddress);
        assertThat(shouldBeEmpty.isEmpty()).isEqualTo(true);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDoesNotAllowNullPerson() {
        Address address = new Address(2,null,"","","","");
        addressService.create(address);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDoesNotAllowNullStreet() {
        Address address = new Address(2,new Person(1,"2","3"),null,"","","");
        addressService.create(address);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDoesNotAllowNullCity() {
        Address address = new Address(2,new Person(1,"2","3"),"",null,"","");
        addressService.create(address);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDoesNotAllowNullState() {
        Address address = new Address(2,new Person(1,"2","3"),"","",null,"");
        addressService.create(address);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateDoesNotAllowNullPostalCode() {
        Address address = new Address(2,new Person(1,"2","3"),"","","",null);
        addressService.create(address);
    }


}
