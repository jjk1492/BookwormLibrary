package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;

/**
 * A visitor of Bookworm Library. Visitors are identified by their unique 10-digit UserID. Visitors are considered
 * the same if they have the same first name, last name, address, and phone number.
 *
 * @author John Knecht V (jjk1492@rit.edu) & Lucas Golden
 */
public class Visitor {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String userID;
    /**
     * Constructor for a visitor, contains name, contact info, and an ID number.
     * @param firstName
     * @param lastName
     * @param address
     * @param phoneNumber
     * @param userID
     */
    public Visitor(String firstName, String lastName, String address, String phoneNumber, String userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
    }

    /**
     * Get this Visitor's ID.
     * @return - The userID String.
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param o - Object being compared to this Visitor
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return firstName.equals(visitor.firstName) &&
                lastName.equals(visitor.lastName) &&
                address.equals(visitor.address) &&
                phoneNumber.equals(visitor.phoneNumber);
    }

    public String toString(){
        String str = this.firstName + " " +  this.lastName + " " + this.address + " " + this.phoneNumber +
                " " + this.userID;
        return str;
    }

    /**
     * Hash a Visitor based on name, address, and phone number since these are the only values used in determining
     * if visitors are already registed.
     * @return - Hashcode for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, phoneNumber);
    }
}
