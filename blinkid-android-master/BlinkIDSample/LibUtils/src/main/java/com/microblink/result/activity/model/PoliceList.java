package com.microblink.result.activity.model;

public class PoliceList {

        private String userResidential;
        private String observation;
        public PoliceList() {

        }

    public PoliceList(String userResidential, String observation) {
        this.userResidential = userResidential;
        this.observation = observation;
    }

    public String getUserResidential() {
            return userResidential;
        }

        public void setUserResidential(String documentNumber) {
            this.userResidential = documentNumber;
        }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}
