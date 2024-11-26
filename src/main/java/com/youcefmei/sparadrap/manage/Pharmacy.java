package com.youcefmei.sparadrap.manage;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.model.*;
import com.youcefmei.sparadrap.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Pharmacy.
 */
public class Pharmacy {
    private static Pharmacy INSTANCE;
    @Getter
    private DoctorService doctorService = new DoctorService();
    @Getter
    private PatientService patientService = new PatientService();
    @Getter
    private PurchaseService purchaseService = new PurchaseService();
    @Getter
    private HealthMutualService healthMutualService = new HealthMutualService();
    @Getter
    private MedicamentService medicamentService = new MedicamentService();


    @Getter
    private ObservableList<DoctorGeneral> doctorGenerals ;
    @Getter
    private ObservableList<DoctorSpecialized> doctorSpecializeds ;
    @Getter
    private ObservableList<DoctorSpeciality> doctorSpecialities ;
    @Getter
    private ObservableList<HealthMutual> healthMutuals;
    @Getter
    private ObservableList<Medicament> medicaments;
    @Getter
    private ObservableList<Patient> patients;
    @Getter
    private ObservableList<Purchase> purchases;
    @Getter
    private ObservableList<Doctor> doctors;
    @Getter @Setter
    private Purchase currentPurchase;

    private Pharmacy(){
        doctorGenerals = doctorService.getDoctorGenerals();
        doctorSpecializeds = doctorService.getDoctorSpecializeds();
        doctors = doctorService.getDoctors();
        doctorSpecialities = doctorService.getDoctorSpecialities();
        patients = patientService.getPatients();
        healthMutuals = healthMutualService.getHealthMutuals();
        purchases = purchaseService.getPurchases();
        medicaments = medicamentService.getMedicaments();
    }

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


    public void addCurrentPurchase() throws DuplicateException, PaymentException {
        purchaseService.addPurchase(currentPurchase);
    }

    public Purchase addPurchase(Purchase purchase) throws DuplicateException, PaymentException {
        return purchaseService.addPurchase(purchase) ;
    }

    public void updatePatient(Patient patient){
        patientService.updatePatient(patient);
    }

    public void addPatient(Patient patient) throws DuplicateException {
        patientService.addPatient(patient);
    }


    public void deletePatient(Patient patient){
        patientService.removePatient(patient);
    }

    public void deleteDoctor(Doctor doctor){
        doctorService.removeDoctor(doctor);
    }


    public void addDoctor(Doctor doctor) throws DuplicateException {
        if (doctor instanceof DoctorGeneral){
            doctorService.addDoctorGeneral( (DoctorGeneral)  doctor);
        } else if (doctor instanceof DoctorSpecialized){
            doctorService.addDoctorSpecialized( (DoctorSpecialized)  doctor);
        }
    }
    public void updateDoctor(Doctor doctor) throws DuplicateException {
        doctorService.updateDoctor(doctor);
    }

}
