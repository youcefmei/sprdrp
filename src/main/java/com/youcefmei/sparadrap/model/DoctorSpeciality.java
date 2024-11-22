package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.util.Arrays;

public class DoctorSpeciality {

    private Integer id;
    private String name;


    public DoctorSpeciality(Integer id, String name) throws InvalidInputException {
        setId(id);
        setName(name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



    /**
     * Sets speciality name.
     *
     * @param name the state name
     * @throws InvalidInputException the invalid input exception
     */
    public void setName(String name) throws InvalidInputException {

        String regex = "^[A-Za-z][A-Za-z\\-ô'éè ]+$";
        if (name != null && ( name.matches(regex) ) ) {
            this.name = name;
        } else{
            throw new InvalidInputException("La spécialité n'est pas valide");
        }
    }

    @Override
    public String toString() {
        return name ;
    }
}
