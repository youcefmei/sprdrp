package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

public class MedicamentCategory {
    private Integer id;
    private String name;


    public MedicamentCategory(Integer id, String name) throws InvalidInputException {
        setId(id);
        setName( name );
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

    public void setName(String name) throws InvalidInputException {
        String regex = "^[A-Za-z][A-Za-z\\-ô'éè ]+$";
        if (name != null && ( name.matches(regex) ) ) {
            this.name = name;
        } else{
            throw new InvalidInputException("Le nom de medicament n'est pas valide : " + name);
        }
    }

    @Override
    public String toString() {
        return name ;
    }
}
