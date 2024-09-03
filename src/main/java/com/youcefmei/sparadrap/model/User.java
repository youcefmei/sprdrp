package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import org.apache.commons.text.WordUtils;

public abstract class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String mail;
    private String address;
    private String areaCode;
    private String city;

//    REGEXTELINT = "^\+(?:[0-9] ?){6,14}[0-9]$";
//    REGEXTELFR = "^(0|\+33|0033)[1-9][0-9]{8}$";
//    REGEXEMAIL = "^[\w.-]+@[\w.-]+\.[a-z]{2,}$";


    public User(String firstName, String lastName,String phone,String mail, String address ,String city, String areaCode) throws InvalidInputException {
        setLastName(lastName);
        setFirstName(firstName);
        setPhone(phone);
        setMail(mail);
        setCity(city);
        setAddress(address);
        setAreaCode(areaCode);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAddress(String address) throws InvalidInputException {
        String regex = "^\\d+\\s+[a-zA-Zéî'çùâûôà\\s]+$";
        if (address != null && ( address.matches(regex) ) ) {
            this.address = address;
        } else{
            throw new InvalidInputException("L'addresse n'est pas valide : " + address);
        }
    }

    public void setAreaCode(String areaCode) throws InvalidInputException {
        String regex = "^(?:0[1-9]|[1-8]\\d|9[0-8]|2[ABab]|97[1-6])\\d{3}$";
        if (areaCode != null && ( areaCode.toLowerCase().matches(regex) ) ) {
            this.areaCode = areaCode.toLowerCase();
        } else{
            throw new InvalidInputException("Le code postale n'est pas valide");
        }
    }

    public void setMail(String mail) throws InvalidInputException {
        if ( (mail != null) && mail.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")){
            this.mail = mail;
        } else{
            throw new InvalidInputException("L'addresse mail n'est pas valide");
        }
    }

    public void setCity(String city) throws InvalidInputException {
        if ( (city != null)  && city.matches("^[a-zA-Z ]*[-a-zA-Z ]*$")){
            this.city = city;
        }else{
            throw new InvalidInputException("L'addresse email n'est pas valide");
        }
    }
    private void setPhone(String phone) throws InvalidInputException {
        if ( (phone != null) && !phone.isEmpty() && ( phone.matches(
                 "^(0|\\+33|0033)[1-9][0-9]{8}$") ) ) {
            this.phone = phone;
        }else{
            throw  new InvalidInputException("Veuillez saisir un numéro de téléphone valide !");
        }
    }

    public void setFirstName(String firstName) throws InvalidInputException {
        if ( ( firstName != null ) && ( !firstName.isBlank() ) && ( firstName.matches(
                "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČ" +
                        "ŠŽ∂ð ,.'-]+$") )) {
            this.firstName = WordUtils.capitalize(firstName.toLowerCase().trim()) ;
        }else{
            throw  new InvalidInputException("Veuillez saisir un prénom valide !");
        }
    }

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
