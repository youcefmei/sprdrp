package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Prescription.
 */
public class Prescription {
    private LocalDate date;
    private Patient patient;
    private Doctor doctor;
    private List<Medicament> medicaments;
    private float priceWithoutMutual;
    private float priceWithMutual;

    /**
     * Instantiates a new Prescription.
     *
     * @param date        the date
     * @param patient     the patient
     * @param doctor      the doctor
     * @param medicaments the medicaments
     * @throws InvalidDateException  the invalid date exception
     * @throws InvalidInputException the invalid input exception
     */
    public Prescription(LocalDate date, Patient patient, Doctor doctor, List<Medicament> medicaments) throws InvalidDateException, InvalidInputException {
        setDate(date);
        setPatient(patient);
        setDoctor(doctor);
        setMedicaments(medicaments);
    }

    /**
     * Gets patient.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Gets doctor.
     *
     * @return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Gets medicaments.
     *
     * @return the medicaments
     */
    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets price without mutual.
     *
     * @return the price without mutual
     */
    public float getPriceWithoutMutual() {
        float totalPrice = 0;
        for (Medicament medicament : medicaments) {
            totalPrice += medicament.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * Gets price with mutual.
     *
     * @return the price with mutual
     */
    public float getPriceWithMutual() {
        if (patient.getHealthMutual() == null){
            return getPriceWithoutMutual();
        }else{
            float totalPrice = 0;
            for (Medicament medicament : medicaments) {
                totalPrice +=  medicament.getTotalPrice() * ( 100 - patient.getHealthMutual().getHealthCareRate() ) / 100 ;
            }
            return totalPrice;
        }
    }

    /**
     * Sets date.
     *
     * @param date the date
     * @throws InvalidDateException the invalid date exception
     */
    public void setDate(LocalDate date) throws InvalidDateException {
        if ( (date == null)  || date.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date d'achat doit etre antérieur à maintenant");
        }else{
            this.date = date;
        }
    }

    /**
     * Sets patient.
     *
     * @param patient the patient
     * @throws InvalidInputException the invalid input exception
     */
    public void setPatient(Patient patient) throws InvalidInputException {
        if (patient == null) {
            throw new InvalidInputException("Veuillez sélectionner un patient");
        } else{
            this.patient = patient;
        }

    }

    /**
     * Sets doctor.
     *
     * @param doctor the doctor
     * @throws InvalidInputException the invalid input exception
     */
    public void setDoctor(Doctor doctor) throws InvalidInputException {
        if (doctor == null) {
            throw new InvalidInputException("Veuillez sélectionner un docteur");
        } else{
            this.doctor = doctor;
        }
    }

    /**
     * Sets medicaments.
     *
     * @param medicaments the medicaments
     */
    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }



}
