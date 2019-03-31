package model;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private Long userID;
    private LocalDateTime timeOfCreation;

    //Account information
    private String username;
    private String password;
    private boolean isEmployee;

    private double fine = 0.0;
    /**
     * Constructor for a visitor, contains name, contact info, and an ID number.
     * @param firstName First name of Visitor
     * @param lastName Last name of Visitor
     * @param address Address of Visitor
     * @param phoneNumber Visitor's phone number
     * @param userID Unique 10 digit ID
     */
    Visitor(String firstName, String lastName, String address, String phoneNumber, Long userID, LocalDateTime time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
        this.timeOfCreation = time.plusNanos(0);
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    /**
     * Get this Visitor's ID.
     * @return - The userID String.
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * Pay user fine in amount
     * @param amount amount to pay
     */
    public void payFine(double amount) {
        fine -= amount;
    }

    /**
     * Get how much the user owes to the library
     * @return fine amount
     */
    public double getFine() {
        return fine;
    }

    /**
     * Add fine amount to user
     * @param amount fine amount
     */
    public void addFine(double amount) {
        this.fine += amount;
    }

    public boolean hasAccount(){
        return username != null;
    }

    public void updateAccount(String username, String password, boolean isEmployee){
        this.username = username;
        this.password = password;
        this.isEmployee = isEmployee;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public String getUsername() {
        return this.username;
    }

    /**
     *
     * @param o - Object being compared to this Visitor
     * @return true if is equal, false otherwise
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

    @Override
    public String toString(){
        return this.firstName + " " +  this.lastName + " " + this.address + " " + this.phoneNumber + " " + this.userID;
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