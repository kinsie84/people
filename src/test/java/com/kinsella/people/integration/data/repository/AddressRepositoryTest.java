package com.kinsella.people.integration.data.repository;

import com.kinsella.people.data.entity.Address;
import com.kinsella.people.data.entity.Person;
import com.kinsella.people.data.repository.AddressRepository;
import com.kinsella.people.data.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;


    @Test
    public void testFindByIdReturnsAddressAndAssociatedPerson() {

        Person testPerson = new Person(1,"Test", "Person");
        entityManager.persist(testPerson);
        entityManager.flush();

        Address testAddress= new Address(1,testPerson,"Test", "Test", "Test", "Test");
        entityManager.persist(testAddress);
        entityManager.flush();

        Optional<Address> testInDatabase = addressRepository.findById(testAddress.getAddressId());

        assertThat(testInDatabase.isPresent()).isEqualTo(true);
        assertThat(testInDatabase.get().getPerson().equals(testPerson));
    }

    @Test
    public void testDeletingAddressDoesNotDeletePerson() {

        Person testPerson = new Person(1,"Test", "Person");
        entityManager.persist(testPerson);
        entityManager.flush();

        Address testAddress= new Address(1,testPerson,"Test", "Test", "Test", "Test");
        entityManager.persist(testAddress);
        entityManager.flush();

        Optional<Person> testPersonInDatabase = personRepository.findById(testPerson.getPersonId());
        Optional<Address> testAddressInDatabase = addressRepository.findById(testAddress.getAddressId());
        assertThat(testPersonInDatabase.isPresent()).isEqualTo(true);
        assertThat(testAddressInDatabase.isPresent()).isEqualTo(true);

        addressRepository.delete(testAddress);
        entityManager.flush();

        Optional<Person> testPersonStillInDatabase = personRepository.findById(testPerson.getPersonId());
        Optional<Address> testAddressNotInDatabase = addressRepository.findById(testAddress.getAddressId());

        assertThat(testPersonStillInDatabase.isPresent()).isEqualTo(true);
        assertThat(testAddressNotInDatabase.isPresent()).isEqualTo(false);
    }

}
