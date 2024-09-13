package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctor.
 */
public abstract class Doctor extends  User {


    private String registrationNb;
    private List<Patient> patients = new ArrayList<>();

    /**
     * Instantiates a new Doctor.
     *
     * @param firstName      the first name
     * @param lastName       the last name
     * @param phone          the phone
     * @param mail           the mail
     * @param address        the address
     * @param city           the city
     * @param areaCode       the area code
     * @param registrationNb the registration nb
     * @throws InvalidInputException the invalid input exception
     */
    public Doctor(String firstName, String lastName, String phone, String mail, String address, String city, String areaCode, String registrationNb) throws InvalidInputException {
        super(firstName, lastName, phone, mail, address, city, areaCode);
        setRegistrationNb(registrationNb);
    }

    /**
     * Gets registration number.
     *
     * @return the registration number
     */
    public String getRegistrationNb() {
        return registrationNb;
    }

    /**
     * Gets patients.
     *
     * @return the patients
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Sets registration number.
     *
     * @param registrationNb the registration number
     * @throws InvalidInputException the invalid input exception
     */
    public void setRegistrationNb(String registrationNb) throws InvalidInputException {

        String regex = "^\\d{10}$";
        if (registrationNb != null && ( registrationNb.matches(regex) ) ) {
            this.registrationNb = registrationNb;
        } else{
            throw new InvalidInputException("Le code d'agréement n'est pas valide, ( dix chiffre uniquement )");
        }
    }

    /**
     * Add patient.
     *
     * @param patient the patient
     */
    public void addPatient(Patient patient)  {

        List<Patient> patientFounds = patients.stream().filter(
                patientTemp -> patientTemp.equals(patient)
        ).toList();

        if (patientFounds.isEmpty()) {
            this.patients.add(patient);
        }
    }

    @Override
    public String toString() {
        return  super.getLastName() + " - " + registrationNb ;
    }
}
