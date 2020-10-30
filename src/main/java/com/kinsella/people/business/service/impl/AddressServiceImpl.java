package com.kinsella.people.business.service.impl;

import com.kinsella.people.business.service.AddressService;
import com.kinsella.people.business.service.PersonService;
import com.kinsella.people.data.entity.Address;
import com.kinsella.people.data.entity.Person;
import com.kinsella.people.data.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonService personService;

    @Override
    public Optional<Address> create(long id, long personId, String street, String city, String state, String postalCode) {
        Optional<Person> person = personService.get(personId);
        if (person.isPresent()) {
            Address address = new Address(id, person.get(), street, city, state, postalCode);
            return create(address);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Address> get(long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> update(long id, String street, String city, String state, String postalCode) {
        Optional<Address> databaseVersion = get(id);
        return databaseVersion.map(value -> {
            value.setStreet(street);
            value.setCity(city);
            value.setState(state);
            value.setPostalCode(postalCode);
            return value;
        });
    }

    @Override
    public boolean delete(long id) {
        if (get(id).isPresent()) {
            addressRepository.deleteById(id);
            return true;
        }
        LOGGER.warn(String.format("Address with id = %d was not found in the database", id));
        return false;
    }

    @Override
    public Optional<Address> create(Address address) {
        if (addressRepository.existsById(address.getAddressId())) {
            LOGGER.warn(String.format("Address with id = %d already exists", address.getAddressId()));
            return Optional.empty();
        } else {
            return Optional.of(addressRepository.save(address));
        }
    }

}
