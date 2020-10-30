package com.kinsella.people.unit.business.service;

import com.kinsella.people.business.service.PersonService;
import com.kinsella.people.business.service.impl.PersonServiceImpl;
import com.kinsella.people.data.entity.Address;
import com.kinsella.people.data.entity.Person;
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
public class PersonServiceTest {
    @TestConfiguration
    static class PersonServiceTestContextConfiguration {
        @Bean
        public PersonService personService() {
            return new PersonServiceImpl();
        }
    }

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Before
    public void setUp() {
        Person test = new Person(1,"Test", "Person");
        Mockito.when(personRepository.findById(1L))
                .thenReturn(Optional.of(test));
        Mockito.when(personRepository.existsById(1L))
                .thenReturn(true);
    }
    @Test
    public void testGetPersonReturnsPersonById() {
        Optional<Person> testInDatabase = personService.get(1);
        assertThat(testInDatabase.isPresent()).isEqualTo(true);
    }

    @Test(expected = NullPointerException.class)
    public void testCreatePersonDoesNotAllowNullFirstName() {
        Person person = new Person(2,null, "Test");
        personService.create(person);
    }

    @Test(expected = NullPointerException.class)
    public void testCreatePersonDoesNotAllowNullLastName() {
        Person person = new Person(2,"Test", null);
        personService.create(person);
    }

    @Test
    public void testCreatePersonDoesNotAllowDupes() {
        Person dupeIdPerson = new Person(1,"Test", "Person");
        Optional<Person> shouldBeEmpty = personService.create(dupeIdPerson);
        assertThat(shouldBeEmpty.isEmpty()).isEqualTo(true);
    }

    @Test
    public void testAddressCannotBeAlteredDirectly() {
        Optional<Person> testPerson = personService.get(1L);
        testPerson.get().getAddresses().add(new Address());
        assertThat(testPerson.get().getAddresses().size()).isEqualTo(0);
    }

}
