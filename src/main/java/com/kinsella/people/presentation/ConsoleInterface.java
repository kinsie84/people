package com.kinsella.people.presentation;


import com.kinsella.people.business.service.AddressService;
import com.kinsella.people.business.service.PersonService;
import com.kinsella.people.data.entity.Address;
import com.kinsella.people.data.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleInterface implements CommandLineRunner {

    @Autowired
    private PersonService personService;
    @Autowired
    private AddressService addressService;

    @Override
    public void run(String... args) {

        System.out.println("*********************************************************");
        System.out.println();
        System.out.println("           Welcome to the People Application             ");
        System.out.println();
        System.out.println("*********************************************************");

        startConsoleUi();

        System.out.println("*********************************************************");
        System.out.println();
        System.out.println("       You are now exiting the People Application       ");
        System.out.println();
        System.out.println("*********************************************************");

    }

    private void startConsoleUi(){
        boolean exit = false;
        Scanner input = new Scanner(System.in);
        while (!exit) {
            displayMenu();
            int option = getOption(input);
            switch (option) {
                case 1:
                    addPerson(input);
                    break;
                case 2:
					editPerson(input);
                    break;
                case 3:
					deletePerson(input);
                    break;
                case 4:
					addAddressToPerson(input);
                    break;
                case 5:
					editAddress(input);
                    break;
                case 6:
					deleteAddress(input);
                    break;
                case 7:
					countPeople();
                    break;
                case 8:
					listPeople();
                    break;
                case 0:
                    exit = true;
                    break;
            }
        }
        input.close();
    }

    private void displayMenu() {
        System.out.println("Choose an option from the menu below");
        System.out.println();
        System.out.println("1.Add Person (id, fistName, lastName)");
        System.out.println("2.Edit Person (firstName, lastName)");
        System.out.println("3.Delete Person (id)");
        System.out.println("4.Add Address to person (id, street, city, state, postalCode)");
        System.out.println("5.Edit Address (street, city, state, postalCode)");
        System.out.println("6.Delete Address (id) ");
        System.out.println("7.Count Number of Persons");
        System.out.println("8.List Persons");
        System.out.println("0.Exit");
        System.out.println();
    }

    private int getOption(Scanner input) {
        boolean valid = false;
        int option = 0;
        while (!valid) {
            System.out.println("Enter option number:");
            option = validateInteger(input);
            if (option >= 0 && option < 9) {
                valid = true;
            } else {
                System.out.println("Please enter a valid option");
            }
        }
        return option;
    }

    private int validateInteger(Scanner input) {
        boolean valid = false;
        int validInteger = 0;
        while (!valid) {
            try {
                validInteger = Integer.parseInt(input.nextLine().trim());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }

        }
        return validInteger;
    }

    private void addPerson(Scanner input) {
        System.out.println("Selected option: Add person");
        System.out.println("Enter ID: ");
        int userId = validateInteger(input);

        System.out.println("Enter first name: ");
        String firstName = input.nextLine().trim();

        System.out.println("Enter last name: ");
        String lastName = input.nextLine().trim();;

        Optional<Person> person = personService.create(userId, firstName, lastName);
        person.ifPresentOrElse((value) -> {
            System.out.println("Person created: " + value.toString());
        }, () -> {
            System.out.println("Person not created, please consult the logs");
        });
        System.out.println();
    }

    private void editPerson(Scanner input) {
        System.out.println("Selected option: Edit person");
        System.out.println("Enter Person ID to edit: ");
        int personId = validateInteger(input);

        if (checkPersonExists(personId)) {
            System.out.println("Enter updated first name: ");
            String firstName = input.nextLine();

            System.out.println("Enter updated last name: ");
            String lastName = input.nextLine();

            Optional<Person> person = personService.update(personId, firstName, lastName);
            person.ifPresentOrElse((value) -> {
                System.out.println("Person updated: " + value.toString());
            }, () -> {
                System.out.println("Person not updated");
            });
            System.out.println();
        }
    }

    private void deletePerson(Scanner input) {
        System.out.println("Selected option: Delete person");
        System.out.println("Enter Person ID to Delete: ");
        int personId = validateInteger(input);

        if (checkPersonExists(personId)) {
            boolean deleted = personService.delete(personId);

            if (deleted) {
                System.out.println("Person deleted successfully");
            } else {
                System.out.println("Person not deleted");
            }
        }
    }

    private void addAddressToPerson(Scanner input) {
        System.out.println("Selected option: Add address to person");

        if (personService.count() == 0) {
            System.out.println("You must have at least one person in order to be able to add an address");
            return;
        }

        System.out.println("Enter Person ID to whom you are adding the address: ");
        int personId = validateInteger(input);

        if (checkPersonExists(personId)) {

            System.out.println("Enter Address ID: ");
            int addressId = validateInteger(input);

            System.out.println("Enter street: ");
            String street = input.nextLine().trim();

            System.out.println("Enter city: ");
            String city = input.nextLine().trim();

            System.out.println("Enter state: ");
            String state = input.nextLine().trim();

            System.out.println("Enter postal code: ");
            String postalCode = input.nextLine().trim();

            Optional<Address> address = addressService.create(addressId, personId, street, city, state, postalCode);
            address.ifPresentOrElse((value) -> {
                System.out.println("Address added: " + value.toString());
            }, () -> {
                System.out.println("Address not created, please consult the logs");
            });
            System.out.println();
        }
    }

    private void editAddress(Scanner input) {
        System.out.println("Selected option: Edit address");
        System.out.println("Enter Address ID to edit: ");
        int addressId = validateInteger(input);

        if (checkAddressExists(addressId)) {
            System.out.println("Enter updated street: ");
            String street = input.nextLine().trim();

            System.out.println("Enter updated city: ");
            String city = input.nextLine().trim();

            System.out.println("Enter updated state: ");
            String state = input.nextLine().trim();

            System.out.println("Enter updated postal code: ");
            String postalCode = input.nextLine().trim();

            Optional<Address> address = addressService.update(addressId, street, city, state, postalCode);
            address.ifPresentOrElse((value) -> {
                System.out.println("Address updated: " + value.toString());
            }, () -> {
                System.out.println("Address not updated");
            });
            System.out.println();
        }
    }

    private void deleteAddress(Scanner input) {
        System.out.println("Selected option: Delete address");
        System.out.println("Enter Address ID to Delete: ");
        int addressId = validateInteger(input);
        if (checkAddressExists(addressId)) {
            boolean deleted = addressService.delete(addressId);
            if (deleted) {
                System.out.println("Address deleted successfully");
            } else {
                System.out.println("Address not deleted, please consult the logs");
            }
        }
    }

    private void countPeople() {
        System.out.println("Selected option: Count people");
        System.out.println("Count people: " + personService.count());
        System.out.println();
    }

    private void listPeople() {
        System.out.println("Selected option: List people");
        System.out.println("List persons: " + personService.getAll());
        System.out.println();
    }

    private boolean checkPersonExists(int personId) {
        if (personService.get(personId).isEmpty()) {
            System.out.printf("Person ID: %d is incorrect, please check for a valid ID and try again via the main menu", personId);
            System.out.println();
            return false;
        }
        return true;
    }

    private boolean checkAddressExists(int addressId) {
        if (addressService.get(addressId).isEmpty()) {
            System.out.printf("Address ID: %d is incorrect, please check for a valid ID and try again via the main menu", addressId);
            System.out.println();
            return false;
        }
        return true;
    }
}
