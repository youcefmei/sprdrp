package com.youcefmei.sparadrap.manage;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pharmacy {
    private static Pharmacy INSTANCE;

    private List<DoctorGeneral> doctorGenerals = new ArrayList<>();
    private List<DoctorSpecialized> doctorSpecializeds = new ArrayList<>();
    private List<HealthMutual> healthMutuals = new ArrayList<>();
    private List<Medicament> Medicaments = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();



    public static Pharmacy getInstance() throws InvalidInputException,DuplicateException {
        if(INSTANCE == null) {
            INSTANCE = new Pharmacy();
        }
        return INSTANCE;
    }


    private Pharmacy() throws InvalidInputException, DuplicateException {
        initDoctorGeneral();
        initPatient();
        initDoctorSpecialized();
//        initHealthMutual();
//        initMedicament();
//        initPrescription();
//        initPurchase();
//

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
        return Medicaments;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    private void initDoctorGeneral() throws InvalidInputException,DuplicateException {
        DoctorGeneral doctor;
        addDoctorGeneral(new DoctorGeneral("firstnamedoctora","lastnamedoctora","0202020202","maildoctora@free.fr","45 rue de la liberté","Nancy","54020","1520364879"));
        addDoctorGeneral(new DoctorGeneral("firstnamedoctorb","lastnamedoctorb","0304060809","maildoctorb@free.fr","18 impasse Herzog","Frouard","54420","5540384872"));
    }

    private void initDoctorSpecialized() throws InvalidInputException,DuplicateException {
        DoctorSpecialized doctor;
        addDoctorSpecialized(new DoctorSpecialized("firstnamedoctorspea","lastnamedoctorspea","0202020202","maildoctorspea@free.fr","45 rue de la liberté","Nancy","54020","1524364879","Neurologie"));
        addDoctorSpecialized(new DoctorSpecialized("firstnamedoctorspeb","lastnamedoctorspb","0304060809","maildoctorspeb@free.fr","18 impasse Herzog","Frouard","54420","5540384872","Dermatologie"));
    }

    private void initPatient() throws InvalidInputException {
        Patient patient;

        patient = new Patient("firstnamepatienta","lastnamepatienta","0101010101","mailpatienta@gmail.com","12 rue des lilas","citypatienta","54000","281025401902191", LocalDate.now().minusYears(30),doctorGenerals.getFirst());
        patients.add(patient);
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

    public void addDoctorGeneral(DoctorGeneral doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor.getRegistrationNb());
        doctorGenerals.add(doctor);
    }

    public void addDoctorSpecialized(DoctorSpecialized doctor) throws DuplicateException {
        checkDoctorDuplicate(doctor.getRegistrationNb());
        doctorSpecializeds.add(doctor);
    }


}
