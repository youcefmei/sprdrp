package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.util.Arrays;

/**
 * The type Doctor specialized.
 */
public class DoctorSpecialized extends Doctor{


    private Integer doctorSpecializedId;
    private DoctorSpeciality speciality;


    /**
     * Instantiates a new Doctor specialized.
     *
     * @param firstName      the first name
     * @param lastName       the last name
     * @param phone          the phone
     * @param mail           the mail
     * @param address        the address
     * @param city           the city
     * @param areaCode       the area code
     * @param registrationNb the registration nb
     * @param speciality     the speciality
     * @throws InvalidInputException the invalid input exception
     */


    public DoctorSpecialized(Integer doctorSpecializedId,String firstName, String lastName, String phone, String mail, String address, String city, String areaCode, String registrationNb,DoctorSpeciality speciality) throws InvalidInputException {
        super(null,firstName, lastName, phone, mail, address, city, areaCode, registrationNb);
        setDoctorSpecializedId(doctorSpecializedId);
        setSpeciality(speciality);
    }


    public Integer getDoctorSpecializedId() {
        return doctorSpecializedId;
    }

    public void setDoctorSpecializedId(Integer doctorSpecializedId) {
        this.doctorSpecializedId = doctorSpecializedId;
    }

    /**
     * Gets speciality.
     *
     * @return the speciality
     */
    public DoctorSpeciality getSpeciality() {
        return speciality;
    }

    /**
     * Sets speciality.
     *
     * @param speciality the speciality
     * @throws InvalidInputException the invalid input exception
     */
    public void setSpeciality(DoctorSpeciality speciality) throws InvalidInputException {
       this.speciality = speciality;




    }

    @Override
    public String toString() {
        return  super.getLastName() + " - " + super.getRegistrationNb() + " - "+ getSpeciality()  ;
    }
}
