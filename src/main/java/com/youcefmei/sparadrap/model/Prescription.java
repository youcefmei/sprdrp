package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;

import java.time.LocalDate;
import java.util.List;

public class Prescription {
    private LocalDate date;
    private Patient patient;
    private Doctor doctor;
    private List<Medicament> medicaments;
    private float priceWithoutMutual;
    private float priceWithMutual;

    public Prescription(LocalDate date, Patient patient, Doctor doctor, List<Medicament> medicaments) throws NullPointerException, InvalidDateException {
        setDate(date);
        setPatient(patient);
        setDoctor(doctor);
        setMedicaments(medicaments);
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getPriceWithoutMutual() {
        float totalPrice = 0;
        for (Medicament medicament : medicaments) {
            totalPrice += medicament.getPrice();
        }
        return totalPrice;
    }


    public float getPriceWithMutual() {
        if (patient.getHealthMutual() == null){
            return getPriceWithoutMutual();
        }else{
            float totalPrice = 0;
            for (Medicament medicament : medicaments) {
                totalPrice += medicament.getPrice() * ( 100 - patient.getHealthMutual().getHealthCareRate() ) / 100;
            }
            return totalPrice;
        }

    }

    public void setDate(LocalDate date) throws InvalidDateException {
        if ( (date == null)  || date.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date d'achat doit etre antérieur à maintenant");
        }else{
            this.date = date;
        }
    }

    public void setPatient(Patient patient) throws NullPointerException {
        if (patient == null) {
            throw new NullPointerException("Le patient ne peut pas etre null");
        } else{
            this.patient = patient;
        }

    }

    public void setDoctor(Doctor doctor) throws NullPointerException {
        if (doctor == null) {
            throw new NullPointerException("Le docteur ne peut pas etre null");
        } else{
            this.doctor = doctor;
        }
    }

    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }



}
