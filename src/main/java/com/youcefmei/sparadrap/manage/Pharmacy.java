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

    private List<Doctor> doctors = new ArrayList<>();

    private Purchase currentPurchase;



    public static Pharmacy getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Pharmacy();
        }
        return INSTANCE;
    }


    private Pharmacy()   {
        initDoctorGeneral();
        initDoctorSpecialized();
        initHealthMutual();
        initMedicament();
        initPatient();
        initPurchase();
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

    //    public List<Prescription> getPrescriptions() {
//        return prescriptions;
//    }

    public List<Purchase> getPurchases() {
        return purchases;
    }


    public void setCurrentPurchase(Purchase purchase) {
        this.currentPurchase = purchase;
    }


    private void initDoctorGeneral() {

        DoctorGeneral doctor;
        try {
            addDoctorGeneral(new DoctorGeneral("firstnamedoctora","lastnamedoctora","0202020202",
                    "maildoctora@free.fr","45 rue de la liberté","Nancy","54020",
                    "1520364879")
            );
            addDoctorGeneral(new DoctorGeneral("firstnamedoctorb","lastnamedoctorb","0304060809",
                    "maildoctorb@free.fr","18 impasse Herzog","Frouard","54420",
                    "5540384872")
            );
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDoctorSpecialized() {
        try {
            addDoctorSpecialized(new DoctorSpecialized("firstnamedoctorspea","lastnamedoctorspea",
                    "0202020202","maildoctorspea@free.fr","45 rue de la liberté","Nancy",
                    "54020","1524364879","Neurologie")
            );
            addDoctorSpecialized(new DoctorSpecialized("firstnamedoctorspeb","lastnamedoctorspb",
                    "0304060809","maildoctorspeb@free.fr","18 impasse Herzog","Frouard",
                    "54420","0740384827","Dermatologie")
            );
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    private void initPatient()  {

        try {
            addPatient(new Patient("firstnamepatienta","lastnamepatienta","0101010101",
                    "mailpatienta@gmail.com","12 rue des lilas","citypatienta","54000",
                    "2810254019021", LocalDate.now().minusYears(30),doctorGenerals.getFirst(),healthMutuals.getFirst())
            );
            addPatient(new Patient("firstnamepatientb","lastnamepatientb","0202020202",
                    "mailpatientb@gmail.com","14 rue des lilas","citypatientb","54222",
                    "2810257019021", LocalDate.now().minusYears(35),doctorGenerals.get(1),null)
            );
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }

    }

    private void initMedicament() {
        try {
            addMedicament(new Medicament("medicament1","Analgésiques",20.0f , 20,
                    LocalDate.now().minusYears(35),true));
            addMedicament(new Medicament("medicament2","Antibiotiques",10.0f , 30,
                    LocalDate.now().minusYears(5),false));
            addMedicament(new Medicament("medicament3","Antituberculeux",70.0f , 10,
                    LocalDate.now().minusYears(15),true));

            addMedicament(new Medicament("medicament4","Analgésiques",20.0f , 20,
                    LocalDate.now().minusYears(35),false));
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    private void initHealthMutual()  {
        System.out.println(Arrays.asList(HealthMutual.mutualNames));
        try {
            addHealthMutual(new HealthMutual("Acoris Mutuelles","0606060606","heathmutual1@gmail.com","10 rue blabla","75000","Paris","75",80));
            addHealthMutual(new HealthMutual("ADREA Mutuelle","0606060606","heathmutual2@gmail.com","12 rue blabla","75000","Paris","75",60));
            addHealthMutual(new HealthMutual("APREVA","0606060606","heathmutual3@gmail.com","12 rue blabla","75000","Paris","75",100));
            addHealthMutual(new HealthMutual("Avenir Mutuelle","0606060606","heathmutual4@gmail.com","12 rue blabla","75000","Paris","75",53));
            addHealthMutual(new HealthMutual("Avenir Santé Mutuelle","0606060606","heathmutual5@gmail.com","12 rue blabla","75000","Paris","75",25));
            addHealthMutual(new HealthMutual("CCMO","0606060606","heathmutual6@gmail.com","12 rue blabla","75000","Paris","75",33));
            addHealthMutual(new HealthMutual("France Mutuelle","0606060606","heathmutual7@gmail.com","12 rue blabla","75000","Paris","75",66));
            addHealthMutual(new HealthMutual("GFP","0606060606","heathmutual8@gmail.com","12 rue blabla","75000","Paris","75",44));
            addHealthMutual(new HealthMutual("Harmonie Mutuelle","0606060606","heathmutual9@gmail.com","12 rue blabla","75000","Paris","75",70));

        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }

    }

    private void initPurchase()  {
        Prescription prescription = null;
        try {
            prescription = new Prescription(LocalDate.now().minusMonths(1),patients.getFirst(),doctorGenerals.getFirst(), List.of(medicaments.get(0),medicaments.get(1)));
            System.out.println(prescription.getMedicaments());
            Purchase purchase = new Purchase(prescription);
            purchase.setPaid(true);
            addPurchase(purchase);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (PaymentException e) {
            throw new RuntimeException(e);
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        }
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


}
