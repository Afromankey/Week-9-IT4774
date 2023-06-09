package edu.capella.bsit.phonebook_nb;

import java.util.ArrayList;

public class PhoneDirectory {
    private ArrayList<PhoneNumber> phoneNumbers;

    public PhoneDirectory() {
        this.phoneNumbers = new ArrayList<>();
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    // Find phone numbers associated with a name
    public ArrayList<PhoneNumber> getPhoneNumbersForName(String name) {
        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();

        for (PhoneNumber phoneNumber : this.phoneNumbers) {
            if (phoneNumber.getName().equals(name)) {
                phoneNumbers.add(phoneNumber);
            }
        }
        return phoneNumbers;
    }

    // Find names (as Strings) associated with a phone number
    public ArrayList<String> getNamesByPhoneNumber(String number) {
        ArrayList<String> names = new ArrayList<>();

        for (PhoneNumber phoneNumber : this.phoneNumbers) {
            if (phoneNumber.getNumber().equals(number)) {
                names.add(phoneNumber.getName());
            }
        }
        return names;
    }
}
