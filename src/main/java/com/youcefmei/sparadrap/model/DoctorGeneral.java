package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

public class DoctorGeneral extends Doctor{



    public DoctorGeneral(String firstName, String lastName, String phone, String mail, String address, String city, String areaCode, String registrationNb) throws InvalidInputException {
        super(firstName, lastName, phone, mail, address, city, areaCode, registrationNb);
    }


}
