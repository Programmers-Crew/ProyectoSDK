package com.microblink.result.activity.model;

public class visitList {

    private String idDate;
    private String idTime;

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

}
