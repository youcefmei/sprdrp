package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Prescription.
 */
public class Prescription {

    private Integer prescriptionId;
    private LocalDate date;
    private Patient patient;
    private Doctor doctor;
    private List<PrescriptionLine> prescriptionLines;
    private float priceWithoutMutual;
    private float priceWithMutual;

    /**
     * Instantiates a new Prescription.
     *
     * @param date        the date
     * @param patient     the patient
     * @param doctor      the doctor
     * @param prescriptionLines the medicaments
     * @throws InvalidDateException  the invalid date exception
     * @throws InvalidInputException the invalid input exception
     */



    public Prescription(Integer prescriptionId, LocalDate date, Patient patient, Doctor doctor, List<PrescriptionLine> prescriptionLines) throws InvalidDateException, InvalidInputException {
        setPrescriptionId(prescriptionId);
        setDate(date);
        setPatient(patient);
        setDoctor(doctor);
    }


    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
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
        for (PrescriptionLine prescriptionLine : prescriptionLines) {
            totalPrice += prescriptionLine.getMedicament().getPrice() * prescriptionLine.getQuantity();
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
            for (PrescriptionLine prescriptionLine : prescriptionLines) {
                totalPrice +=  ( prescriptionLine.getMedicament().getPrice() * prescriptionLine.getQuantity() ) * ( 100 - patient.getHealthMutual().getHealthCareRate() ) / 100 ;
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

    public List<PrescriptionLine> getPrescriptionLines() {
        return prescriptionLines;
    }

    public void setPrescriptionLines(List<PrescriptionLine> prescriptionLines) {
        this.prescriptionLines = prescriptionLines;
    }
}
