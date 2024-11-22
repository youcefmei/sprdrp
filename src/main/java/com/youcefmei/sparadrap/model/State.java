package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

public class State {

    private Integer id;
    private String name;
    private String code;


    public State(Integer id, String name, String code) throws InvalidInputException {
        setId(id);
        setName(name);
        setCode(code);
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



    public String getCode() {
        return code;
    }


    public void setCode(String code) throws InvalidInputException {

        String regex = "^(0[1-9]|[1-8][0-9]|9[0-5]|2[ab]|97[1-6])$";
        if (code != null && ( code.matches(regex) ) ) {
            this.code = code;
        } else{
            throw new InvalidInputException("Le code du département n'est pas valide");
        }

    }


    /**
     * Sets state.
     *
     * @param name the state name
     * @throws InvalidInputException the invalid input exception
     */
    public void setName(String name) throws InvalidInputException {

        String regex = "^[A-Za-z][A-Za-z\\-ô'éè ]+$";
        if (name != null && ( name.toLowerCase().matches(regex) ) ) {
            this.name = name.toLowerCase();
        } else{
            throw new InvalidInputException("Le nom de département n'est pas valide");
        }
    }


}
