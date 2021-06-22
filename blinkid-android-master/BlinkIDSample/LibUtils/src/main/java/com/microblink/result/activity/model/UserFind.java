package com.microblink.result.activity.model;

public class UserFind {

    private String firstName;
    private String firstLasName;
    private String birthDate;
    private Integer	age;
    private String birthPlace;
    private String expeditionDate;
    private String expirationDate;
    private String address;
    private String nationality;
    private String shipper;
    private String sex;
    private String civilStatus;
    private String documentTyoe;
    private String documentNumber;

    public UserFind() {
    }

    public UserFind(String firstName, String firstLasName, String birthDate, Integer age, String birthPlace, String expeditionDate, String expirationDate, String address, String nationality, String shipper, String sex, String civilStatus, String documentTyoe, String documentNumber) {
        this.firstName = firstName;
        this.firstLasName = firstLasName;
        this.birthDate = birthDate;
        this.age = age;
        this.birthPlace = birthPlace;
        this.expeditionDate = expeditionDate;
        this.expirationDate = expirationDate;
        this.address = address;
        this.nationality = nationality;
        this.shipper = shipper;
        this.sex = sex;
        this.civilStatus = civilStatus;
        this.documentTyoe = documentTyoe;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(String expeditionDate) {
        this.expeditionDate = expeditionDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getDocumentTyoe() {
        return documentTyoe;
    }

    public void setDocumentTyoe(String documentTyoe) {
        this.documentTyoe = documentTyoe;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
