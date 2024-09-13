package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * The type Patient.
 */
public class Patient extends User{
    private String secuId;
    private LocalDate birthDate;
    private String birthDateStr;
    private DoctorGeneral familyDoctor;
    private HealthMutual healthMutual;
    private List<DoctorSpecialized> doctorSpecializeds;


    /**
     * Instantiates a new Patient.
     *
     * @param firstName    the first name
     * @param lastName     the last name
     * @param phone        the phone
     * @param mail         the mail
     * @param address      the address
     * @param city         the city
     * @param areaCode     the area code
     * @param secuId       the secu id
     * @param birthDate    the birth date
     * @param familyDoctor the family doctor
     * @throws InvalidInputException the invalid input exception
     * @throws InvalidDateException  the invalid date exception
     */
    public Patient(String firstName, String lastName, String phone, String mail, String address, String city,
                   String areaCode, String secuId, LocalDate birthDate, DoctorGeneral familyDoctor
    ) throws InvalidInputException, InvalidDateException {

        super(firstName, lastName, phone, mail, address, city, areaCode);
        setSecuId(secuId);
        setBirthDate(birthDate);
        setFamilyDoctor(familyDoctor);
    }


    /**
     * Instantiates a new Patient.
     *
     * @param firstName    the first name
     * @param lastName     the last name
     * @param phone        the phone
     * @param mail         the mail
     * @param address      the address
     * @param city         the city
     * @param areaCode     the area code
     * @param secuId       the secu id
     * @param birthDate    the birth date
     * @param familyDoctor the family doctor
     * @param healthMutual the health mutual
     * @throws InvalidInputException the invalid input exception
     * @throws InvalidDateException  the invalid date exception
     */
    public Patient(String firstName, String lastName, String phone, String mail, String address, String city,
                   String areaCode, String secuId, LocalDate birthDate, DoctorGeneral familyDoctor,HealthMutual healthMutual
    ) throws InvalidInputException, InvalidDateException {

        super(firstName, lastName, phone, mail, address, city, areaCode);
        setSecuId(secuId);
        setBirthDate(birthDate);
        setFamilyDoctor(familyDoctor);
        setHealthMutual(healthMutual);
    }

    /**
     * Gets secu id.
     *
     * @return the secu id
     */
    public String getSecuId() {
        return secuId;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Gets birth date formatted.
     *
     * @return the birth date formatted
     */
    public String getBirthDateStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return  formatter.format(birthDate);
    }

    /**
     * Gets family doctor.
     *
     * @return the family doctor
     */
    public DoctorGeneral getFamilyDoctor() {
        return familyDoctor;
    }

    /**
     * Gets doctor specializeds.
     *
     * @return the doctor specializeds
     */
    public List<DoctorSpecialized> getDoctorSpecializeds() {
        return doctorSpecializeds;
    }

    /**
     * Gets health mutual.
     *
     * @return the health mutual
     */
    public HealthMutual getHealthMutual() {
        return healthMutual;
    }

    /**
     * Sets family doctor.
     *
     * @param familyDoctor the family doctor
     */
    public void setFamilyDoctor(DoctorGeneral familyDoctor) {
        this.familyDoctor = familyDoctor;
    }


    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     * @throws InvalidDateException the invalid date exception
     */
    public void setBirthDate(LocalDate birthDate) throws InvalidDateException {
        if ( (birthDate == null)  || birthDate.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date de naissance doit etre antérieur à aujourd'hui");
        }else{
            this.birthDate = birthDate;
        }
    }

    /**
     * Sets secu id.
     *
     * @param secuId the secu id
     * @throws InvalidInputException the invalid input exception
     */
    public void setSecuId(String secuId) throws InvalidInputException {

        String regex = "^(1|2)\\d{2}(0[1-9]|[1-9][0-9]|2[ABab]|97[1-6])\\d{6}\\d{2}$";
        if (secuId != null && ( secuId.matches(regex) ) ) {
            this.secuId = secuId;
        } else{
            throw new InvalidInputException("Le numéro de sécurité social n'est pas valide");
        }

    }

    /**
     * Sets health mutual.
     *
     * @param healthMutual the health mutual
     */
    public void setHealthMutual(HealthMutual healthMutual) {
        this.healthMutual = healthMutual;
    }

    @Override
    public String toString() {
        return  getLastName() + " - " + secuId ;
    }


}
