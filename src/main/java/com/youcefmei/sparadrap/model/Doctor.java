package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctor.
 */
@Getter
public  class Doctor extends  User {

    private Integer doctorId;
    /**
     * -- GETTER --
     *  Gets registration number.
     *
     * @return the registration number
     */
    private String registrationNb;
    /**
     * -- GETTER --
     *  Gets patients.
     *
     * @return the patients
     */
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


    public Doctor(Integer doctorId, String firstName, String lastName, String phone, String mail, String address, String city, String areaCode, String registrationNb) throws InvalidInputException {
        super(null,firstName, lastName, phone, mail, address, city, areaCode);
        setRegistrationNb(registrationNb);
        setDoctorId(doctorId);
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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
