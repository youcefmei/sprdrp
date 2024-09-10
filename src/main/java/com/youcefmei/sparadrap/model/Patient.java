package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import org.apache.commons.validator.GenericValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


public class Patient extends User{
    private String secuId;
    private LocalDate birthDate;
    private String birthDateStr;
    private DoctorGeneral familyDoctor;
    private HealthMutual healthMutual;
    private List<DoctorSpecialized> doctorSpecializeds;



    public Patient(String firstName, String lastName, String phone, String mail, String address, String city,
                   String areaCode, String secuId, LocalDate birthDate, DoctorGeneral familyDoctor
    ) throws InvalidInputException, InvalidDateException {

        super(firstName, lastName, phone, mail, address, city, areaCode);
        setSecuId(secuId);
        setBirthDate(birthDate);
        setFamilyDoctor(familyDoctor);
    }


    public Patient(String firstName, String lastName, String phone, String mail, String address, String city,
                   String areaCode, String secuId, LocalDate birthDate, DoctorGeneral familyDoctor,HealthMutual healthMutual
    ) throws InvalidInputException, InvalidDateException {

        super(firstName, lastName, phone, mail, address, city, areaCode);
        setSecuId(secuId);
        setBirthDate(birthDate);
        setFamilyDoctor(familyDoctor);
        setHealthMutual(healthMutual);
    }

    public String getSecuId() {
        return secuId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateStr() {
        return birthDateStr;
    }

    public DoctorGeneral getFamilyDoctor() {
        return familyDoctor;
    }

    public List<DoctorSpecialized> getDoctorSpecializeds() {
        return doctorSpecializeds;
    }

    public HealthMutual getHealthMutual() {
        return healthMutual;
    }

    public void setFamilyDoctor(DoctorGeneral familyDoctor) {
        this.familyDoctor = familyDoctor;
    }


    public void setBirthDate(LocalDate birthDate) throws InvalidDateException {
        if ( (birthDate == null)  || birthDate.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date de naissance doit etre antérieur à aujourd'hui");
        }else{
            this.birthDate = birthDate;
        }
    }

    public void setSecuId(String secuId) throws InvalidInputException {

        String regex = "^(1|2)\\d{2}(0[1-9]|[1-9][0-9]|2[ABab]|97[1-6])\\d{6}\\d{2}$";
        if (secuId != null && ( secuId.matches(regex) ) ) {
            this.secuId = secuId;
        } else{
            throw new InvalidInputException("Le numéro de sécurité social n'est pas valide");
        }

    }

    private void setBirthDateStr(String birthDateStr) throws InvalidInputException, InvalidDateException {
        if (GenericValidator.isDate(birthDateStr,"dd-MM-yyyy",true)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse( birthDateStr, formatter);
            setBirthDate(date);
            this.birthDateStr = birthDateStr;
        }
        else{
            throw new InvalidInputException("La date d'inscription n'est pas valide");
        }
    }

    public void setHealthMutual(HealthMutual healthMutual) {
        this.healthMutual = healthMutual;
    }

    @Override
    public String toString() {
        return  getLastName() + " - " + secuId ;
    }


}
