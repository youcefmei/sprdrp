package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Medicament category.
 */
public class MedicamentCategory {

    @Getter @Setter
    private Integer id;
    @Getter
    private String name;


    /**
     * Instantiates a new Medicament category.
     *
     * @param id   the id
     * @param name the name
     * @throws InvalidInputException the invalid input exception
     */
    public MedicamentCategory(Integer id, String name) throws InvalidInputException {
        setId(id);
        setName( name );
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @throws InvalidInputException the invalid input exception
     */
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
