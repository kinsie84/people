package com.kinsella.people.business.service.impl;

import com.kinsella.people.business.service.PersonService;
import com.kinsella.people.data.entity.Address;
import com.kinsella.people.data.entity.Person;
import com.kinsella.people.data.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;


    @Override
    public Optional<Person> create(long id, String firstName, String lastName) {
        Person person = new Person(id,firstName,lastName);
        return create(person);
    }

    @Override
    public Optional<Person> get(long id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        Iterable<Person> personIterable = personRepository.findAll();
        personIterable.iterator().forEachRemaining(personList::add);
        return personList;
    }

    @Override
    public Optional<Person> update(long id, String firstName, String LastName) {
        Optional<Person> databaseVersion = get(id);
        return databaseVersion.map(value -> {
            value.setFirstName(firstName);
            value.setLastName(LastName);
            return personRepository.save(value);
        });
    }

    @Override
    public boolean removeAddress(Person person, Address address) {
        if (person.getAddresses().contains(address)) {
            boolean removed = person.removeAddress(address);
            if (removed) {
                personRepository.save(person);
                return true;
            }
            LOGGER.warn(String.format("Address with id = %d was not removed from Person with id = %d", address.getAddressId(), person.getPersonId()));
        } else {
            LOGGER.warn(String.format("Address with id = %d was not found to be associated with Person of id = %d", address.getAddressId(), person.getPersonId()));
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (get(id).isPresent()) {
            personRepository.deleteById(id);
            return true;
        }
        LOGGER.warn(String.format("Person with id = %d was not found in the database", id));
        return false;
    }

    @Override
    public Optional<Person> create(Person person) {
        if (personRepository.existsById(person.getPersonId())) {
            LOGGER.warn(String.format("Person with id = %d already exists", person.getPersonId()));
            return Optional.empty();
        } else {
            return Optional.of(personRepository.save(person));
        }
    }

    @Override
    public long count() {
        return personRepository.count();
    }


}
