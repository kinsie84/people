package com.kinsella.people.unit.business.service;

import com.kinsella.people.business.service.PersonService;
import com.kinsella.people.business.service.impl.PersonServiceImpl;
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
    }
    @Test
    public void testGetReturnsPersonById() {
        Optional<Person> testInDatabase = personService.get(1);
        assertThat(testInDatabase.isPresent()).isEqualTo(true);
    }
}
