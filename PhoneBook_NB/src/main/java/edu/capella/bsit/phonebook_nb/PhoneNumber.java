package edu.capella.bsit.phonebook_nb;

public class PhoneNumber {
    private String name;
    private String number;

    // Just a parameterized constructor (no default constructor)
    public PhoneNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }

    // Only getters & toString() to keep this brief
    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return this.name + ": " + this.number;
    }
}
