package com.kinsella.people.business.service;

import com.kinsella.people.data.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<Address> create(long id, long personId, String street, String city, String state, String postalCode);

    Optional<Address> create(Address address);

    Optional<Address> get(long id);

    List<Address> getAll();

    Optional<Address> update(long id, String street, String city, String state, String postalCode);

    boolean delete(long id);


}
