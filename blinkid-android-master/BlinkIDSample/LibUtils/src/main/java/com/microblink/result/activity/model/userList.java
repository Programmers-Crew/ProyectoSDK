package com.microblink.result.activity.model;

public class userList {

    private String documentNumber;
    private String firstName;
    private String firstLasName;

    public userList() {

    }

    public userList(String documentNumber, String firstName, String firstLasName) {
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.firstLasName = firstLasName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstLasName() {
        return firstLasName;
    }

    public void setFirstLasName(String firstLasName) {
        this.firstLasName = firstLasName;
    }
}
