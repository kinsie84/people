package com.kinsella.people.business.service;

import com.kinsella.people.data.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> create(Person person);

    Optional<Person> create(long id, String firstName, String lastName);

    Optional<Person> get(long id);

    List<Person> getAll();

    Optional<Person> update(long id, String firstName, String LastName);

    boolean delete(long id);

    long count();
}
