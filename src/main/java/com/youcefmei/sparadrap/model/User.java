package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import org.apache.commons.text.WordUtils;

/**
 * Abstract class that represent a user
 */
public abstract class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String mail;
    private String address;
    private String areaCode;
    private String city;

    /**
     * Constructs a new User with the specified details.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param phone     the phone number of the user
     * @param mail      the email address of the user
     * @param address   the physical address of the user
     * @param city      the city of the user
     * @param areaCode  the area code of the user's address
     * @throws InvalidInputException if any input is invalid
     */
    public User(String firstName, String lastName,String phone,String mail, String address ,String city, String areaCode) throws InvalidInputException {
        setLastName(lastName);
        setFirstName(firstName);
        setPhone(phone);
        setMail(mail);
        setCity(city);
        setAddress(address);
        setAreaCode(areaCode);
    }

    /**
     * Returns the first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the phone number
     *
     * @return the first name
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the email address
     *
     * @return the email address
     */
    public String getMail() {
        return mail;
    }


    /**
     * Returns the city
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the address
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets area code.
     *
     * @return the area code
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets address.
     *
     * @param address the address
     * @throws InvalidInputException the invalid input exception
     */
    public void setAddress(String address) throws InvalidInputException {
        String regex = "^\\d+\\s+[a-zA-Zéî'çùâûôà\\s]+$";
        if (address != null && ( address.matches(regex) ) ) {
            this.address = address;
        } else{
            throw new InvalidInputException("L'addresse n'est pas valide : " + address);
        }
    }

    /**
     * Sets area code.
     *
     * @param areaCode the area code
     * @throws InvalidInputException the invalid input exception
     */
    public void setAreaCode(String areaCode) throws InvalidInputException {
        String regex = "^(?:0[1-9]|[1-8]\\d|9[0-8]|2[ABab]|97[1-6])\\d{3}$";
        if (areaCode != null && ( areaCode.toLowerCase().matches(regex) ) ) {
            this.areaCode = areaCode.toLowerCase();
        } else{
            throw new InvalidInputException("Le code postale n'est pas valide");
        }
    }

    /**
     * Sets mail.
     *
     * @param mail the mail
     * @throws InvalidInputException the invalid input exception
     */
    public void setMail(String mail) throws InvalidInputException {
        if ( (mail != null) && mail.trim().toLowerCase().matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")){
            this.mail = mail.trim().toLowerCase();
        } else{
            throw new InvalidInputException("L'addresse mail n'est pas valide");
        }
    }

    /**
     * Sets city.
     *
     * @param city the city
     * @throws InvalidInputException the invalid input exception
     */
    public void setCity(String city) throws InvalidInputException {
        if ( (city != null)  && ( !city.isBlank() )  && city.matches("^[a-zA-Z ]*[-a-zA-Z ]*$")){
            this.city = WordUtils.capitalize(city.toLowerCase().trim());
        }else{
            throw new InvalidInputException("La ville n'est pas valide");
        }
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     * @throws InvalidInputException the invalid input exception
     */
    public void setPhone(String phone) throws InvalidInputException {
        if ( (phone != null) && !phone.isEmpty() && ( phone.matches(
                 "^(0|\\+33|0033)[1-9][0-9]{8}$") ) ) {
            this.phone = phone;
        }else{
            throw  new InvalidInputException("Veuillez saisir un numéro de téléphone valide !");
        }
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     * @throws InvalidInputException the invalid input exception
     */
    public void setFirstName(String firstName) throws InvalidInputException {
        if ( ( firstName != null ) && ( !firstName.isBlank() ) && ( firstName.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.firstName = WordUtils.capitalize(firstName.toLowerCase().trim()) ;
        }else{
            throw  new InvalidInputException("Veuillez saisir un prénom valide !");
        }
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     * @throws InvalidInputException the invalid input exception
     */
    public void setLastName(String lastName) throws InvalidInputException {
        if ( ( lastName != null ) && ( !lastName.isBlank() ) && ( lastName.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.lastName = WordUtils.capitalize(lastName.toLowerCase().trim()) ;

        }else{
            throw  new InvalidInputException("Veuillez saisir un nom valide ! ");
        }
    }

}
