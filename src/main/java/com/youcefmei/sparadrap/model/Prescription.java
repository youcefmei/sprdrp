package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;

import java.time.LocalDate;
import java.util.List;

public class Prescription {
    private LocalDate date;
    private Patient patient;
    private Doctor doctor;
    private List<Medicament> medicaments;

    public Prescription(LocalDate date, Patient patient, Doctor doctor, List<Medicament> medicaments) throws Exception {
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


    public void setDate(LocalDate date) throws InvalidDateException {
        if ( (date == null)  || date.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date d'achat doit etre antérieur à maintenant");
        }else{
            this.date = date;
        }
    }

    public void setPatient(Patient patient) throws Exception {
        if (patient == null) {
            throw new Exception("Le patient ne peut pas etre null");
        } else{
            this.patient = patient;
        }

    }

    public void setDoctor(Doctor doctor) throws Exception {
        if (doctor == null) {
            throw new Exception("Le docteur ne peut pas etre null");
        } else{
            this.doctor = doctor;
        }
    }

    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }
}
