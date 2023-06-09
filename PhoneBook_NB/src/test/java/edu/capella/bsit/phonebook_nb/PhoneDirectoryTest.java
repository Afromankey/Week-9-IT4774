package edu.capella.bsit.phonebook_nb;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PhoneDirectoryTest {
    // Object used in tests; must be private. Intialized in setUp() method
    private PhoneDirectory phoneDirectory = null;
    
    public PhoneDirectoryTest() {
    }
    
    @BeforeEach   // Test runner in NetBeans doesn't pay attentio to @BeforeEach
    // This method must be called by each test method. 
    public void setUp() {
        System.out.println("Running setup()...");
        phoneDirectory = new PhoneDirectory();
        phoneDirectory.addPhoneNumber(new PhoneNumber("John Doe", "555-555-5555"));
        phoneDirectory.addPhoneNumber(new PhoneNumber("Jane Doe", "555-555-5555"));
        phoneDirectory.addPhoneNumber(new PhoneNumber("John Doe", "555-555-5556"));
        phoneDirectory.addPhoneNumber(new PhoneNumber("Jane Doe", "555-555-5557"));
    }
    

    /**
     * Test of getPhoneNumbersForName method, of class PhoneDirectory.
     */
    @Test
    public void testGetPhoneNumbersForName() {
        System.out.println("testGetPhoneNumbersForName");
        setUp();
        ArrayList<PhoneNumber> phoneNumbers = phoneDirectory.getPhoneNumbersForName("John Doe");
        // All assertions must be true for test to pass. 
        assertEquals(2, phoneNumbers.size());
        assertEquals("John Doe: 555-555-5555", phoneNumbers.get(0).toString());
        assertEquals("John Doe: 555-555-5556", phoneNumbers.get(1).toString());
    }
    
    @Test
    public void testGetPhoneNumbersForNameNotFound() {
        System.out.println("testGetPhoneNumbersForNameNotFound");
        setUp();
        // This shouldn't find any phone numbers
        ArrayList<PhoneNumber> phoneNumbers = phoneDirectory.getPhoneNumbersForName("John Smith");
        assertEquals(0, phoneNumbers.size());
    }

    /**
     * Test of getNamesByPhoneNumber method, of class PhoneDirectory.
     */
    @Test
    public void testGetNamesByPhoneNumber() {
        System.out.println("getNamesByPhoneNumber");
        setUp();
        ArrayList<String> names = phoneDirectory.getNamesByPhoneNumber("555-555-5555");
        assertEquals(2, names.size());
        assertEquals("John Doe", names.get(0));
        assertEquals("Jane Doe", names.get(1));
    } 
    
    @Test
    public void testGetNamesByPhoneNumberNotFound() {
        System.out.println("testGetNamesByPhoneNumberNotFound");
        setUp();
        ArrayList<String> names = phoneDirectory.getNamesByPhoneNumber("555-555-5558");
        assertEquals(0, names.size());
    }
}
