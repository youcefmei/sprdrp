package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.util.Arrays;
import java.util.List;

/**
 * The type Health mutual.
 */
public class HealthMutual {

    /**
     * The constant mutualNames.
     */
    public final static String[] mutualNames = {"Acoris Mutuelles","ADREA Mutuelle","APREVA","Avenir Mutuelle","Avenir Santé Mutuelle","CCMO","France Mutuelle","GFP","Harmonie Mutuelle"};
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String areaCode;
    private String city;
    private String state;
    private float healthCareRate;

    /**
     * Instantiates a new Health mutual.
     *
     * @param name           the name
     * @param phone          the phone
     * @param mail           the mail
     * @param address        the address
     * @param areaCode       the area code
     * @param city           the city
     * @param state          the state
     * @param healthCareRate the health care rate
     * @throws InvalidInputException the invalid input exception
     */
    public HealthMutual(String name, String phone, String mail, String address, String areaCode, String city, String state, float healthCareRate) throws InvalidInputException {
        setName(name);
        setPhone(phone);
        setMail(mail);
        setAddress(address);
        setAreaCode(areaCode);
        setCity(city);
        setState(state);
        setHealthCareRate(healthCareRate);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets mail.
     *
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Gets address.
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
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Gets health care rate.
     *
     * @return the health care rate
     */
    public float getHealthCareRate() {
        return healthCareRate;
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
     * Sets state.
     *
     * @param state the state
     * @throws InvalidInputException the invalid input exception
     */
    public void setState(String state) throws InvalidInputException {
        String regex = "^(0[1-9]|[1-8][0-9]|9[0-5]|2[ab]|97[1-6])$";
        if (state != null && ( state.toLowerCase().matches(regex) ) ) {
            this.state = state.toLowerCase();
        } else{
            throw new InvalidInputException("Le département n'est pas valide");
        }
    }

    /**
     * Sets health care rate.
     *
     * @param healthCareRate the health care rate
     * @throws InvalidInputException the invalid input exception
     */
    public void setHealthCareRate(float healthCareRate) throws InvalidInputException {
        if ( healthCareRate < 0  ) {
            throw new InvalidInputException("Le taux de remboursement ne peut être negatif");
        } else if ( healthCareRate > 100  ) {
                throw new InvalidInputException("Le taux de remboursement ne peut être supérieur à 100");
        } else{
            this.healthCareRate = healthCareRate;
        }
    }

    /**
     * Sets address.
     *
     * @param address the address
     * @throws InvalidInputException the invalid input exception
     */
    public void setAddress(String address) throws InvalidInputException {
        String regex = "^\\d+\\s+([a-zA-Z\\s]+)$";
        if (address != null && ( address.matches(regex) ) ) {
            this.address = address;
        } else{
            throw new InvalidInputException("L'addresse n'est pas valide");
        }
    }

    /**
     * Sets mail.
     *
     * @param mail the mail
     * @throws InvalidInputException the invalid input exception
     */
    public void setMail(String mail) throws InvalidInputException {
        if ( (mail != null) && mail.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")){
            this.mail = mail;
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

    /**
     * Sets name.
     *
     * @param name the name
     * @throws InvalidInputException the invalid input exception
     */
    public void setName(String name) throws InvalidInputException {
        List<String>  mutualNameFound = Arrays.asList(mutualNames).stream().filter(
                mutualNameTemp -> mutualNameTemp.toLowerCase().equals(name.trim().toLowerCase())
        ).toList();

        if (mutualNameFound.isEmpty() ) {
            throw  new InvalidInputException("Veuillez saisir une mutuelle valide ! \nVeuillez choisir entre:" + mutualNames.toString());
        }else{
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return name;
    }


}