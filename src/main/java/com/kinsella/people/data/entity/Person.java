package com.kinsella.people.data.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

    @Id
    private long personId;

    @OneToMany(targetEntity = Address.class, mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Address> addresses;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    public Person() {
        super();
        this.addresses = new HashSet<>();
    }

    public Person(long personId, String firstName, String lastName) {
        this.personId = personId;
        this.addresses = new HashSet<>();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return String.format("Person ID = %d. Person Name = %s %s. # Addresses = %s", getPersonId(), getFirstName(), getLastName(), getAddresses().size());
    }
}
