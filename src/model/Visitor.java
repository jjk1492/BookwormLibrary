package model;

public class Visitor {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String userID;

    public Visitor(String firstName, String lastName, String address, String phoneNumber, String userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }


}
