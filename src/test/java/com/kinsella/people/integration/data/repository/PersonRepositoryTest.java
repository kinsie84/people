package com.kinsella.people.integration.data.repository;

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
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testFindByIdReturnsPerson() {

        Person test = new Person(1,"Test", "Person");
        entityManager.persist(test);
        entityManager.flush();

        Optional<Person> testInDatabase = personRepository.findById(test.getPersonId());

        assertThat(testInDatabase.isPresent());
        assertThat(testInDatabase.get().getFirstName().equals("Test"));
        assertThat(testInDatabase.get().getLastName().equals("Peter"));
    }

    @Test
    public void testFindByIdReturnsEmptyOptional() {

        Person test = new Person(1,"Test", "Person");
        entityManager.persist(test);
        entityManager.flush();

        Optional<Person> testInDatabase = personRepository.findById(2L);

        assertThat(testInDatabase.isEmpty());
    }

    @Test
    public void testPersonIsRemovedWhenDeleteCalled() {

        Person test = new Person(1,"Test", "Person");
        entityManager.persist(test);
        entityManager.flush();

        personRepository.delete(test);

        Optional<Person> shouldNotExist = personRepository.findById(test.getPersonId());

        assertThat(shouldNotExist.isEmpty());
    }

}
