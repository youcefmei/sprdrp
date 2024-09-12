package com.youcefmei.sparadrap.manage;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy {
    private static Pharmacy INSTANCE;

    private ObservableList<DoctorGeneral> doctorGenerals = FXCollections.observableArrayList();
    private ObservableList<DoctorSpecialized> doctorSpecializeds = FXCollections.observableArrayList();
    private ObservableList<HealthMutual> healthMutuals = FXCollections.observableArrayList();
    private ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
//    private List<Purchase> purchases = new ArrayList<>();
    private ObservableList<Purchase> purchases = FXCollections.observableArrayList();
//    private CopyOnWriteArrayList<Purchase> purchases = new CopyOnWriteArrayList<Purchase>();
    private Patient currentPatientEdit;

    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private Purchase currentPurchase;

    static {
        INSTANCE = new Pharmacy();
    }

    private Pharmacy(){

    }


    public static Pharmacy getInstance() {
        return INSTANCE;
    }

    public Purchase getCurrentPurchase() {
        return currentPurchase;
    }

    public ObservableList<DoctorGeneral> getDoctorGenerals() {
        return doctorGenerals;
    }

    public ObservableList<DoctorSpecialized> getDoctorSpecializeds() {
        return doctorSpecializeds;
    }

    public ObservableList<HealthMutual> getHealthMutuals() {
        return healthMutuals;
    }

    public ObservableList<Medicament> getMedicaments() {
        return medicaments;
    }

    public ObservableList<Patient> getPatients() {
        return patients;
    }

    public ObservableList<Doctor> getDoctors() {
        return doctors;
    }


    public Patient getCurrentPatientEdit() {
        return currentPatientEdit;
    }

    public void setCurrentPatientEdit(Patient currentPatientEdit) {
        this.currentPatientEdit = currentPatientEdit;
    }

    public ObservableList<Purchase> getPurchases() {
        return purchases;
    }


    public void setCurrentPurchase(Purchase purchase) {
        this.currentPurchase = purchase;
    }



    private void checkDoctorDuplicate(Doctor doctor) throws DuplicateException {
        if (doctor == null && doctors.contains(null)) {
            throw new DuplicateException("Il y a déja un docteur vide");
        } else if (doctor != null) {
            for (Doctor doctorTemp : doctorGenerals) {
                if ((doctorTemp !=null) && (doctorTemp.getRegistrationNb().equals(doctor.getRegistrationNb())) ) {
                    throw new DuplicateException("Il y a déja un docteur ayant ce numero d'agrement");
                }
            }
            for (Doctor doctorTemp : doctorGenerals) {
                if ((doctorTemp !=null) && (doctorTemp.getRegistrationNb().equals(doctor.getRegistrationNb()))) {
                    throw new DuplicateException("Il y a déja un docteur ayant ce numero d'agrement");
                }
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

    private void checkHealthMutualDuplicate(HealthMutual healthMutual ) throws DuplicateException {
        if (healthMutual == null && healthMutuals.contains(null)) {
            throw new DuplicateException("Il y a déja une mutuelle vide");
        } else if (healthMutual != null) {
            for ( HealthMutual healthMutualTemp : healthMutuals) {
                if (healthMutualTemp.getName().equals(healthMutual.getName())) {
                    throw new DuplicateException("Il y a déja une mutuelle nommée ainsi");
                }
            }
        }
    }

    public void addPatient(Patient patient) throws DuplicateException {
        checkPatientDuplicate(patient.getSecuId());
        patients.add(patient);
    }

    public void addDoctorGeneral(DoctorGeneral doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor);
        doctorGenerals.add(doctor);
        doctors.add( doctor);
    }

    public void addDoctorSpecialized(DoctorSpecialized doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor);
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
        checkHealthMutualDuplicate(healthMutual);
        healthMutuals.add(healthMutual);
    }

    public void removePatient(Patient patient) {
//        patients =  FXCollections.observableArrayList(
//                patients.stream().filter(
//                        patientTemp -> !patientTemp.getSecuId().equals( patient.getSecuId() )
//                ).toList()
//        );
        patients.remove(patient);

    }


}
