package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

/**
 * The type Doctor general.
 */
public class DoctorGeneral extends Doctor{


    /**
     * Instantiates a new Doctor general.
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
    public DoctorGeneral(String firstName, String lastName, String phone, String mail, String address, String city, String areaCode, String registrationNb) throws InvalidInputException {
        super(firstName, lastName, phone, mail, address, city, areaCode, registrationNb);
    }


}
