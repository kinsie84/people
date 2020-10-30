package com.kinsella.people.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    private long addressId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personId")
    private Person person;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String postalCode;

    public Address() {
        super();
    }

    public Address(long addressId, Person person, String street, String city, String state, String postalCode) {
        this.addressId = addressId;
        this.person = person;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return String.format("Address ID = %d. Person = %d. Address = %s, %s, %s, %s", getAddressId(), getPerson().getPersonId(), getStreet(), getPostalCode(), getCity(), getState());
    }
}
