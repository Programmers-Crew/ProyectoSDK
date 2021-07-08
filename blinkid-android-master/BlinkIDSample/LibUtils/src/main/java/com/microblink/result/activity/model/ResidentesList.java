package com.microblink.result.activity.model;

public class ResidentesList {

    private String userId;
    private String userName;
    private String userNoHouse;

    public ResidentesList() {

    }

    public ResidentesList(String userId, String userName, String userNoHouse) {
        this.userId = userId;
        this.userName = userName;
        this.userNoHouse = userNoHouse;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String documentNumber) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNoHouse() {
        return userNoHouse;
    }

    public void setUserNoHouse(String userNoHouse) {
        this.userNoHouse = userNoHouse;
    }
}
