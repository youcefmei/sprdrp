package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

public abstract class Doctor extends  User {

    private String registrationNb;
    private List<Patient> patients = new ArrayList<Patient>();

    public Doctor(String firstName, String lastName, String phone, String mail, String address, String city, String areaCode, String registrationNb) throws InvalidInputException {
        super(firstName, lastName, phone, mail, address, city, areaCode);
        setRegistrationNb(registrationNb);
    }

    public String getRegistrationNb() {
        return registrationNb;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setRegistrationNb(String registrationNb) throws InvalidInputException {

        String regex = "^\\d{10}$";
        if (registrationNb != null && ( registrationNb.matches(regex) ) ) {
            this.registrationNb = registrationNb;
        } else{
            throw new InvalidInputException("Le code d'agréement n'est pas valide, ( dix chiffre uniquement )");
        }
    }

    public void addPatient(Patient patient)  {

        List<Patient> patientFounds = patients.stream().filter(
                patientTemp -> patientTemp.equals(patient)
        ).toList();

        if (patientFounds.isEmpty()) {
            this.patients.add(patient);
        }
    }

    public Patient getPatientBySecuId(int index) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public String toString() {
        return  super.getLastName() + " - " + registrationNb ;
    }
}
