package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.util.List;

/**
 * The type Doctor specialized.
 */
public class DoctorSpecialized extends Doctor{

    private String speciality;
    private  List<String> specialties = List.of("Andrologie","Urologie","Cardiologie","Gynécologie","Obstétrique","Pédiatrie",
            "Otorhinolaryngologie","Neurologie","Dermatologie","Gastro-entérologie","Rhumatologie","Néphrologie",
            "Hématologie","Ophtalmologie","Pneumologie","Psychiatrie"
    );

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
    public DoctorSpecialized(String firstName, String lastName, String phone, String mail, String address, String city, String areaCode, String registrationNb,String speciality) throws InvalidInputException {
        super(firstName, lastName, phone, mail, address, city, areaCode, registrationNb);
        setSpeciality(speciality);
    }

    /**
     * Gets speciality.
     *
     * @return the speciality
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Sets speciality.
     *
     * @param speciality the speciality
     * @throws InvalidInputException the invalid input exception
     */
    public void setSpeciality(String speciality) throws InvalidInputException {
        List<String> specialityFounds = specialties.stream().filter(
                specialityTemp -> specialityTemp.equals(speciality)
        ).toList();

        if (!specialityFounds.isEmpty()) {
            this.speciality = speciality;
        } else{
            throw new InvalidInputException("La spécialité n'est pas valide");
        }

    }

    @Override
    public String toString() {
        return  super.getLastName() + " - " + super.getRegistrationNb() + " - "+ getSpeciality()  ;
    }
}
