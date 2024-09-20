package com.youcefmei.sparadrap.manage;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Pharmacy.
 */
public class Pharmacy {
    private static Pharmacy INSTANCE;

    private ObservableList<DoctorGeneral> doctorGenerals = FXCollections.observableArrayList();
    private ObservableList<DoctorSpecialized> doctorSpecializeds = FXCollections.observableArrayList();
    private ObservableList<HealthMutual> healthMutuals = FXCollections.observableArrayList();
    private ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<Purchase> purchases = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private Purchase currentPurchase;

    static {
        INSTANCE = new Pharmacy();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Pharmacy getInstance() {
        return INSTANCE;
    }

    /**
     * Gets current purchase.
     *
     * @return the current purchase
     */
    public Purchase getCurrentPurchase() {
        return currentPurchase;
    }

    /**
     * Gets doctor generals.
     *
     * @return the doctor generals
     */
    public ObservableList<DoctorGeneral> getDoctorGenerals() {
        return doctorGenerals;
    }

    /**
     * Gets doctor specializeds.
     *
     * @return the doctor specializeds
     */
    public ObservableList<DoctorSpecialized> getDoctorSpecializeds() {
        return doctorSpecializeds;
    }

    /**
     * Gets health mutuals.
     *
     * @return the health mutuals
     */
    public ObservableList<HealthMutual> getHealthMutuals() {
        return healthMutuals;
    }

    /**
     * Gets medicaments.
     *
     * @return the medicaments
     */
    public ObservableList<Medicament> getMedicaments() {
        return medicaments;
    }

    /**
     * Gets patients.
     *
     * @return the patients
     */
    public ObservableList<Patient> getPatients() {
        return patients;
    }

    /**
     * Gets doctors.
     *
     * @return the doctors
     */
    public ObservableList<Doctor> getDoctors() {
        return doctors;
    }



    /**
     * Gets purchases.
     *
     * @return the purchases
     */
    public ObservableList<Purchase> getPurchases() {
        return purchases;
    }


    /**
     * Sets current purchase.
     *
     * @param purchase the purchase
     */
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
            if (purchaseTemp.getID().equals(purchaseId)) {
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

    /**
     * Add patient.
     *
     * @param patient the patient
     * @throws DuplicateException the duplicate exception
     */
    public void addPatient(Patient patient) throws DuplicateException {
        checkPatientDuplicate(patient.getSecuId());
        patients.add(patient);
    }

    /**
     * Add doctor general.
     *
     * @param doctor the doctor
     * @throws DuplicateException the duplicate exception
     */
    public void addDoctorGeneral(DoctorGeneral doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor);
        doctorGenerals.add(doctor);
        doctors.add( doctor);
    }

    /**
     * Add doctor specialized.
     *
     * @param doctor the doctor
     * @throws DuplicateException the duplicate exception
     */
    public void addDoctorSpecialized(DoctorSpecialized doctor) throws DuplicateException {
        if (doctor != null) {
            checkDoctorDuplicate(doctor);
            doctorSpecializeds.add(doctor);
            doctors.add(doctor);
        }
    }


    /**
     * Add medicament.
     *
     * @param medicament the medicament
     * @throws DuplicateException the duplicate exception
     */
    public void addMedicament(Medicament medicament) throws DuplicateException {
        checkMedicamentDuplicate(medicament.getTitle());
        medicaments.add(medicament);
    }

    /**
     * Add purchase.
     *
     * @param purchase the purchase
     * @throws DuplicateException the duplicate exception
     * @throws PaymentException   the payment exception
     */
    public void addPurchase(Purchase purchase) throws DuplicateException, PaymentException {
        checkPurchaseDuplicate(purchase.getID());
        if (purchase.isPaid() ){
            purchases.add(purchase);
        }
        else{
            throw new PaymentException("Le paiement n'est pas encore effectué");
        }
    }

    /**
     * Add current purchase.
     *
     * @throws DuplicateException the duplicate exception
     * @throws PaymentException   the payment exception
     */
    public void addCurrentPurchase() throws DuplicateException, PaymentException {
        addPurchase(currentPurchase);
    }

    /**
     * Add health mutual.
     *
     * @param healthMutual the health mutual
     * @throws DuplicateException the duplicate exception
     */
    public void addHealthMutual(HealthMutual healthMutual) throws DuplicateException {
        checkHealthMutualDuplicate(healthMutual);
        healthMutuals.add(healthMutual);
    }

    /**
     * Remove patient.
     *
     * @param patient the patient
     */
    public void removePatient(Patient patient) {
        patients.remove(patient);

    }


    public void removeDoctor(Doctor doctor) {
        if (doctor instanceof DoctorSpecialized){
            doctorSpecializeds.remove(doctor);
        }else if (doctor instanceof Doctor){
            doctorGenerals.remove(doctor);
        }
        doctors.remove( doctor);
    }


}
