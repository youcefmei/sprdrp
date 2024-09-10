package com.youcefmei.sparadrap;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class Main extends Application {
    public static void main(String[] args) throws InvalidInputException, DuplicateException, InvalidDateException, PaymentException {
        Pharmacy pharmacy = Pharmacy.getInstance();

        // populate pharmacy with data
        Main main = new  Main();
        main.initDoctorGeneral(pharmacy);
        main.initDoctorSpecialized(pharmacy);
        main.initHealthMutual(pharmacy);
        main.initMedicament(pharmacy);
        main.initPatient(pharmacy);
        main.initPurchase(pharmacy);
        System.out.println(pharmacy.getPatients());
        System.out.println(pharmacy.getDoctorGenerals());
//        Main main = new Main();
//        main.start();

        launch();
    }


    private void initDoctorGeneral(Pharmacy pharmacy) {

        DoctorGeneral doctor;
        try {
            pharmacy.addDoctorGeneral(new DoctorGeneral("firstnamedoctora","lastnamedoctora","0202020202",
                    "maildoctora@free.fr","45 rue de la liberté","Nancy","54020",
                    "1520364879")
            );
            pharmacy.addDoctorGeneral(new DoctorGeneral("firstnamedoctorb","lastnamedoctorb","0304060809",
                    "maildoctorb@free.fr","18 impasse Herzog","Frouard","54420",
                    "5540384872")
            );
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDoctorSpecialized(Pharmacy pharmacy) {
        try {
            pharmacy.addDoctorSpecialized(new DoctorSpecialized("firstnamedoctorspea","lastnamedoctorspea",
                    "0202020202","maildoctorspea@free.fr","45 rue de la liberté","Nancy",
                    "54020","1524364879","Neurologie")
            );
            pharmacy.addDoctorSpecialized(new DoctorSpecialized("firstnamedoctorspeb","lastnamedoctorspb",
                    "0304060809","maildoctorspeb@free.fr","18 impasse Herzog","Frouard",
                    "54420","0740384827","Dermatologie")
            );
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    private void initPatient(Pharmacy pharmacy)  {

        try {
            pharmacy.addPatient(new Patient("firstnamepatienta","lastnamepatienta","0101010101",
                    "mailpatienta@gmail.com","12 rue des lilas","citypatienta","54000",
                    "2810254019021", LocalDate.now().minusYears(30),pharmacy.getDoctorGenerals().getFirst(),pharmacy.getHealthMutuals().getFirst())
            );
            pharmacy.addPatient(new Patient("firstnamepatientb","lastnamepatientb","0202020202",
                    "mailpatientb@gmail.com","14 rue des lilas","citypatientb","54222",
                    "2810257019021", LocalDate.now().minusYears(35),pharmacy.getDoctorGenerals().get(1),null)
            );
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }

    }

    private void initMedicament(Pharmacy pharmacy) {
        try {
            pharmacy.addMedicament(new Medicament("medicament1","Analgésiques",20.0f , 20,
                    LocalDate.now().minusYears(35),true));
            pharmacy.addMedicament(new Medicament("medicament2","Antibiotiques",10.0f , 30,
                    LocalDate.now().minusYears(5),false));
            pharmacy.addMedicament(new Medicament("medicament3","Antituberculeux",70.0f , 10,
                    LocalDate.now().minusYears(15),true));

            pharmacy.addMedicament(new Medicament("medicament4","Analgésiques",20.0f , 20,
                    LocalDate.now().minusYears(35),false));
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    private void initHealthMutual(Pharmacy pharmacy)  {
        System.out.println(Arrays.asList(HealthMutual.mutualNames));
        try {
            pharmacy.addHealthMutual(new HealthMutual("Acoris Mutuelles","0606060606","heathmutual1@gmail.com","10 rue blabla","75000","Paris","75",80));
            pharmacy.addHealthMutual(new HealthMutual("ADREA Mutuelle","0606060606","heathmutual2@gmail.com","12 rue blabla","75000","Paris","75",60));
            pharmacy.addHealthMutual(new HealthMutual("APREVA","0606060606","heathmutual3@gmail.com","12 rue blabla","75000","Paris","75",100));
            pharmacy.addHealthMutual(new HealthMutual("Avenir Mutuelle","0606060606","heathmutual4@gmail.com","12 rue blabla","75000","Paris","75",53));
            pharmacy.addHealthMutual(new HealthMutual("Avenir Santé Mutuelle","0606060606","heathmutual5@gmail.com","12 rue blabla","75000","Paris","75",25));
            pharmacy.addHealthMutual(new HealthMutual("CCMO","0606060606","heathmutual6@gmail.com","12 rue blabla","75000","Paris","75",33));
            pharmacy.addHealthMutual(new HealthMutual("France Mutuelle","0606060606","heathmutual7@gmail.com","12 rue blabla","75000","Paris","75",66));
            pharmacy.addHealthMutual(new HealthMutual("GFP","0606060606","heathmutual8@gmail.com","12 rue blabla","75000","Paris","75",44));
            pharmacy.addHealthMutual(new HealthMutual("Harmonie Mutuelle","0606060606","heathmutual9@gmail.com","12 rue blabla","75000","Paris","75",70));

        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }

    }

    private void initPurchase(Pharmacy pharmacy)  {
        Prescription prescription = null;
        try {
            prescription = new Prescription(LocalDate.now().minusMonths(1),
                    pharmacy.getPatients().getFirst(),pharmacy.getDoctorGenerals().getFirst(),
                    List.of(pharmacy.getMedicaments().get(0),pharmacy.getMedicaments().get(1))
            );
            System.out.println(prescription.getMedicaments());
            Purchase purchase = new Purchase(prescription);
            purchase.setPaid(true);
            pharmacy.addPurchase(purchase);
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

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/views/asset/icon.png")));

        stage.setTitle("Sparadrap");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }




}