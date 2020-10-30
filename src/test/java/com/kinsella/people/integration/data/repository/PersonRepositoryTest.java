package com.kinsella.people.integration.data.repository;

import com.kinsella.people.data.entity.Person;
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

    @Test
    public void whenFindByName_thenReturnEmployee() {

        Person test = new Person(1,"Test", "Person");
        entityManager.persist(test);
        entityManager.flush();

        Optional<Person> testInDatabase = personRepository.findById(test.getPersonId());

        assertThat(testInDatabase.isPresent());
        assertThat(testInDatabase.get().getFirstName().equals("Test"));
        assertThat(testInDatabase.get().getLastName().equals("Peter"));
    }
}
