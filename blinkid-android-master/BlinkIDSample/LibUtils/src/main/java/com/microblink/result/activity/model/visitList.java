package com.microblink.result.activity.model;

public class visitList {

    private String idDate;
    private String idTime;
    private String observation;
    private String userNoHouse;

    public visitList() {

    }

    public visitList(String idDate, String idTime) {
        this.idDate = idDate;
        this.idTime = idTime;
    }

    public String getIdDate() {
        return idDate;
    }

    public void setIdDate(String idDate) {
        this.idDate = idDate;
    }

    public String getIdTime() {
        return idTime;
    }

    public void setIdTime(String idTime) {
        this.idTime = idTime;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getUserNoHouse() {
        return userNoHouse;
    }

    public void setUserNoHouse(String userNoHouse) {
        this.userNoHouse = userNoHouse;
    }
}
