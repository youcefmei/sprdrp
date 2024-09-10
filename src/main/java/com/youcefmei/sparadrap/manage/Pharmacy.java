package com.youcefmei.sparadrap.manage;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pharmacy {
    private static Pharmacy INSTANCE;

    private List<DoctorGeneral> doctorGenerals = new ArrayList<>();
    private List<DoctorSpecialized> doctorSpecializeds = new ArrayList<>();
    private List<HealthMutual> healthMutuals = new ArrayList<>();
    private List<Medicament> medicaments = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();
    private Patient currentPatientEdit;

    private List<Doctor> doctors = new ArrayList<>();
    private Purchase currentPurchase;

    public static Pharmacy getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Pharmacy();
        }
        return INSTANCE;
    }

    public Purchase getCurrentPurchase() {
        return currentPurchase;
    }

    public List<DoctorGeneral> getDoctorGenerals() {
        return doctorGenerals;
    }

    public List<DoctorSpecialized> getDoctorSpecializeds() {
        return doctorSpecializeds;
    }

    public List<HealthMutual> getHealthMutuals() {
        return healthMutuals;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }


    public Patient getCurrentPatientEdit() {
        return currentPatientEdit;
    }

    public void setCurrentPatientEdit(Patient currentPatientEdit) {
        this.currentPatientEdit = currentPatientEdit;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }


    public void setCurrentPurchase(Purchase purchase) {
        this.currentPurchase = purchase;
    }



    private void checkDoctorDuplicate(String doctorRegistrationNb) throws DuplicateException {
        for (Doctor doctorTemp : doctorGenerals) {
            if (doctorTemp.getRegistrationNb().equals(doctorRegistrationNb)) {
                throw new DuplicateException("Il y a déja un docteur ayant ce numero d'agrement");
            }
        }
        for (Doctor doctorTemp : doctorGenerals) {
            if (doctorTemp.getRegistrationNb().equals(doctorRegistrationNb)) {
                throw new DuplicateException("Il y a déja un docteur ayant ce numero d'agrement");
            }
        }
    }

    private void checkPatientDuplicate(String secuId ) throws DuplicateException {
        for (Patient patientTemp : patients) {
            if (patientTemp.getSecuId().equals(secuId)) {
                throw new DuplicateException("Il y a déja un patient ayant ce numero de secu");
            }
        }
    }

    private void checkMedicamentDuplicate(String name ) throws DuplicateException {
        for (Medicament medicamentTemp : medicaments) {
            if (medicamentTemp.getTitle().equals(name)) {
                throw new DuplicateException("Il y a déja un medicament nommé comme ceci");
            }
        }
    }

    private void checkPurchaseDuplicate(String purchaseId ) throws DuplicateException {
        for (Purchase purchaseTemp : purchases) {
            if (purchaseTemp.getId().equals(purchaseId)) {
                throw new DuplicateException("Il y a déja une facture avec ce numéro");
            }
        }
    }

    private void checkHealthMutualDuplicate(String name ) throws DuplicateException {
        for ( HealthMutual healthMutualTemp : healthMutuals) {
            if (healthMutualTemp.getName().equals(name)) {
                throw new DuplicateException("Il y a déja une mutuelle nommée ainsi");
            }
        }
    }

    public void addPatient(Patient patient) throws DuplicateException {
        checkPatientDuplicate(patient.getSecuId());
        patients.add(patient);
    }

    public void addDoctorGeneral(DoctorGeneral doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor.getRegistrationNb());
        doctorGenerals.add(doctor);
        doctors.add( doctor);
    }

    public void addDoctorSpecialized(DoctorSpecialized doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor.getRegistrationNb());
        doctorSpecializeds.add(doctor);
        doctors.add( doctor);
    }
    

    public void addMedicament(Medicament medicament) throws DuplicateException {
        checkMedicamentDuplicate(medicament.getTitle());
        medicaments.add(medicament);
    }

    public void addPurchase(Purchase purchase) throws DuplicateException, PaymentException {
        checkPurchaseDuplicate(purchase.getId());
        if (purchase.isPaid() ){
            purchases.add(purchase);
        }
        else{
            throw new PaymentException("Le paiement n'est pas encore effectué");
        }
    }

    public void addCurrentPurchase() throws DuplicateException, PaymentException {
        addPurchase(currentPurchase);
    }

    public void addHealthMutual(HealthMutual healthMutual) throws DuplicateException {
        checkHealthMutualDuplicate(healthMutual.getName());
        healthMutuals.add(healthMutual);
    }

    public void removePatient(Patient patient) {
        patients =  new ArrayList<>(
                patients.stream().filter(
                        patientTemp -> !patientTemp.getSecuId().equals(patient.getSecuId())
                ).toList()

        );

    }


}
