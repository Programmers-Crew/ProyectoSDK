package com.microblink;

import com.microblink.results.date.DateResult;

public class DpiModel {
    String firstName;
    String lastName;
    String fullName;
    String localizedName;
    String sex;
    String age;
    String address;
    DateResult birth;
    String documentNumber;
    String country;
    String placeBirth;
    String nacionality1;
    DateResult dateExpire;
    String maritalStatus;

    public DpiModel(String firstName, String lastName, String fullName, String localizedName, String sex, String age, String address, DateResult birth, String documentNumber, String country, String placeBirth, String nacionality1, DateResult dateExpire) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.localizedName = localizedName;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.birth = birth;
        this.documentNumber = documentNumber;
        this.country = country;
        this.placeBirth = placeBirth;
        this.nacionality1 = nacionality1;
        this.dateExpire = dateExpire;
        this.maritalStatus = maritalStatus;
    }

    public DpiModel() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DateResult getBirth() {
        return birth;
    }

    public void setBirth(DateResult birth) {
        this.birth = birth;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlaceBirth() {
        return placeBirth;
    }

    public void setPlaceBirth(String placeBirth) {
        this.placeBirth = placeBirth;
    }

    public String getNacionality1() {
        return nacionality1;
    }

    public void setNacionality1(String nacionality1) {
        this.nacionality1 = nacionality1;
    }

    public DateResult getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(DateResult dateExpire) {
        this.dateExpire = dateExpire;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Override
    public String toString() {
        return "com.microblink.DpiModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", localizedName='" + localizedName + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                ", birth=" + birth +
                ", documentNumber='" + documentNumber + '\'' +
                ", country='" + country + '\'' +
                ", placeBirth='" + placeBirth + '\'' +
                ", nacionality1='" + nacionality1 + '\'' +
                ", dateExpire=" + dateExpire +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}
