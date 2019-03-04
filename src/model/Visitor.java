package model;

import java.util.Objects;
import java.util.Queue;

/**
 * A visitor of Bookworm Library. Visitors are identified by their unique 10-digit UserID. Visitors are considered
 * the same if they have the same first name, last name, address, and phone number.
 *
 * @author John Knecht V (jjk1492@rit.edu)
 */
public class Visitor {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String userID;

    private double fine = 0.0;
    /**
     * Constructor for a visitor, contains name, contact info, and an ID number.
     * @param firstName First name of Visitor
     * @param lastName Last name of Visitor
     * @param address Address of Visitor
     * @param phoneNumber Visitor's phone number
     * @param userID Unique 10 digit ID
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

    public void payFine(double amount){
        fine -= amount;
    }

    public double getFine(){
        return fine;
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
