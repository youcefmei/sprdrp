package com.youcefmei.sparadrap.controller;


import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.*;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * The type Purchase with prescription controller.
 */
public class PurchaseWithPrescriptionController implements Initializable {

    @FXML
    private ComboBox<Medicament> medicamentNameCombo;

    @FXML
    private ComboBox<Patient> patientNameSecuCombo;

    @FXML
    private ComboBox<Doctor> doctorNameRegistrationNbCombo;

    @FXML
    private Spinner<Integer> medicamentQuantitySpinner;

    @FXML
    private Text purchaseTotalPriceWithMutualText;

    @FXML
    private TextField patientSearchTextField,doctorSearchTextField,medicamentSearchTextField;

    @FXML
    private TableView medicamentTable;
//


    @FXML
    private TableColumn<PurchaseItem, String> medicamentTitleCol  ;

    @FXML
    private TableColumn<PurchaseItem, Float> medicamentPriceCol,medicamentTotalPriceCol,medicamentTotalPriceWithMutualCol;

    @FXML
    private TableColumn<PurchaseItem, Integer> medicamentQuantityCol;


    private Pharmacy pharmacy = Pharmacy.getInstance();
    private Prescription prescription;

    private Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un medicament");
    private FilteredList<Medicament> filteredMedicaments;
    private FilteredList<Doctor> filteredDoctors;
    private FilteredList<Patient> filteredPatients;
    private ObservableList<PurchaseItem> medicamentTableItems;
    private Patient patient;
    private Doctor doctor;
    private float healthMutualRate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Patient combo init

        patientNameSecuCombo.setItems(pharmacy.getPatients());
//        patientNameSecuCombo.getSelectionModel().selectFirst();

        // Doctor combo init

        doctorNameRegistrationNbCombo.setItems(pharmacy.getDoctors());

        // Medicament init
            // combo init

        medicamentNameCombo.setItems(pharmacy.getMedicaments());
            // spinner init
        medicamentQuantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        50
                )
        );

        initMedicamentTable();

    }


    @FXML
    private void handleSearchPatient(KeyEvent event) {

        filteredPatients = new FilteredList<>(pharmacy.getPatients());
        filteredPatients.setPredicate(
                new Predicate<Patient>() {
                    @Override
                    public boolean test(Patient patient) {
                        return patient.toString().toLowerCase().contains(patientSearchTextField.getText().toLowerCase());
                    }
                }
        );
        patientNameSecuCombo.setItems(filteredPatients);
        patientNameSecuCombo.getSelectionModel().selectFirst();
    }

//
    @FXML
    private void handleFilterSelectedPatient(ActionEvent event) {
        patient = patientNameSecuCombo.getSelectionModel().getSelectedItem();
        if ( (patient != null) && (patient.getHealthMutual() != null )) {
            healthMutualRate =  patient.getHealthMutual().getHealthCareRate();
        }
        disableIfDoctorAndPatient();
    }

    @FXML
    private void handleRegisterPurchase(ActionEvent event) {
        try {

            List<PrescriptionLine> prescriptionLines = new ArrayList<>();
            medicamentTable.getItems().forEach(
                    item -> {
                        PurchaseItem purchaseItem = (PurchaseItem) item;
                        PrescriptionLine prescriptionLine = new PrescriptionLine(null,purchaseItem.getQuantity(),purchaseItem.getMedicament());
                        prescriptionLines.add((prescriptionLine));
                    }
            );

            prescription = new Prescription(null, LocalDate.now(), patient,doctor, prescriptionLines);
            System.out.println( prescription.getPrescriptionLines() );
            Purchase purchase = new Purchase(null, prescription);
            purchase.setPaid( true );
            pharmacy.setCurrentPurchase( purchase );
            pharmacy.addPurchase(purchase);
            alertInfo.setContentText("L'achat a bien été enregistré: "
                    + pharmacy.getCurrentPurchase().getTotalAmountWithMutual()
                    + "€\nId: " + purchase.getRef()
                    + "\nDate: " + purchase.getDatetimeStr()
            );
            alertInfo.showAndWait();
            pharmacy.setCurrentPurchase(new Purchase(null));

            handleClearPurchase(new ActionEvent());
        } catch ( InvalidInputException | InvalidDateException | DuplicateException  | PaymentException  e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
    }

    @FXML
    private void handleSearchDoctor(KeyEvent event) {
        filteredDoctors = new FilteredList<>(pharmacy.getDoctors());
        filteredDoctors.setPredicate(
                new Predicate<Doctor>() {
                    @Override
                    public boolean test(Doctor doctor) {
                        return doctor.toString().toLowerCase().contains(doctorSearchTextField.getText().toLowerCase());
                    }
                }
        );
        doctorNameRegistrationNbCombo.setItems(filteredDoctors);
        doctorNameRegistrationNbCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleClearPurchase(ActionEvent event) {
        doctorNameRegistrationNbCombo.getSelectionModel().clearSelection();
        patientNameSecuCombo.getSelectionModel().clearSelection();
        medicamentNameCombo.getSelectionModel().clearSelection();
        doctorNameRegistrationNbCombo.setDisable(false);
        patientNameSecuCombo.setDisable(false);
        purchaseTotalPriceWithMutualText.setText("0.0 €");
        initMedicamentTable();
    }

    @FXML
    private void handleFilterSelectedDoctor(ActionEvent event) {
        doctor = doctorNameRegistrationNbCombo.getSelectionModel().getSelectedItem();
        disableIfDoctorAndPatient();
    }

    @FXML
    private void handleSearchMedicament(KeyEvent event) {
        filteredMedicaments = new FilteredList<>(pharmacy.getMedicaments());
        filteredMedicaments.setPredicate(
                new Predicate<Medicament>() {
                    @Override
                    public boolean test(Medicament medicament) {
                        return medicament.getTitle().toLowerCase().contains(medicamentSearchTextField.getText().toLowerCase());
                    }
                }
        );
        medicamentNameCombo.setItems(filteredMedicaments);
        medicamentNameCombo.getSelectionModel().selectFirst();

    }

    @FXML
    private void handleFilterSelectedMedicament(ActionEvent event) {
    }

    @FXML
    private void handleAddMedicament(ActionEvent event) {
        Medicament medicament = medicamentNameCombo.getSelectionModel().getSelectedItem();
        if (patientNameSecuCombo.getSelectionModel().getSelectedItem() == null) {
            alertInfo.setContentText("Veuillez selectionner un patient");
            alertInfo.showAndWait();
        }
        else if (doctorNameRegistrationNbCombo.getSelectionModel().getSelectedItem() == null) {
            alertInfo.setContentText("Veuillez selectionner un docteur");
            alertInfo.showAndWait();
        }
        else if (medicament == null) {
            alertInfo.setContentText("Veuillez selectionner un medicament");
            alertInfo.showAndWait();

        } else{
            boolean isAddedAlready = false;
            PurchaseItem purchaseItemToRemove = null;

            for (PurchaseItem purchaseItem : medicamentTableItems){
                if ((purchaseItem.getMedicament().getTitle().equals(medicament.getTitle())) && (purchaseItem.getQuantity() == medicamentQuantitySpinner.getValue())) {
                    alertInfo.setContentText("Déjà dans le panier. Vous pouvez changer \nla quantité pour modier la commande");
                    alertInfo.showAndWait();
                    isAddedAlready = true;
                } else if (purchaseItem.getMedicament().getTitle().equals(medicament.getTitle())) {
                        purchaseItemToRemove = purchaseItem;
                }
            }

            if (purchaseItemToRemove != null) {
                medicamentTableItems.remove(purchaseItemToRemove);
            }

            if (!isAddedAlready){

                PurchaseItem purchaseItem = null;
                try {
                    purchaseItem = new PurchaseItem(null,medicamentQuantitySpinner.getValue(),medicament);
                } catch (InvalidInputException e) {
                    alertInfo.setContentText(e.getMessage());
                    alertInfo.showAndWait();

                }
                medicamentTableItems.add(purchaseItem);
                medicamentTable.refresh();

            }
        }
        calculateDisplayTotalPriceWithMutual();
    }

    //
    @FXML
    private void handleDeleteMedicament(ActionEvent event) {

        alertDelete.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                medicamentTableItems.remove(medicamentTable.getSelectionModel().getSelectedItem());
                calculateDisplayTotalPriceWithMutual();

            }
        });

    }

    private void disableIfDoctorAndPatient(){

        if ( ( patient != null ) && ( doctor != null )){
            patientNameSecuCombo.setDisable(true);
            doctorNameRegistrationNbCombo.setDisable(true);
            patient = patientNameSecuCombo.getSelectionModel().getSelectedItem();
            doctor = doctorNameRegistrationNbCombo.getSelectionModel().getSelectedItem();
            if ( patient.getHealthMutual() != null){

                healthMutualRate = patient.getHealthMutual().getHealthCareRate();
            } else {
                healthMutualRate = 0;
            }

            initMedicamentTable();
        }
    }


    private void initMedicamentTable() {
        medicamentTitleCol.setCellValueFactory(
                cellData -> new SimpleStringProperty( cellData.getValue().getMedicament().getTitle() )
        );

        medicamentPriceCol.setCellValueFactory(
                cellData ->  new SimpleFloatProperty(  cellData.getValue().getMedicament().getPrice() ).asObject()
        );


//        medicamentPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        medicamentTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        medicamentTotalPriceWithMutualCol.setCellValueFactory(cellData -> ( new SimpleFloatProperty(
                cellData.getValue().getTotalPrice() )).multiply( ( 100 - healthMutualRate ) / 100 ).asObject()
        );
        medicamentQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        medicamentTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        medicamentPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        medicamentTotalPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        medicamentTotalPriceWithMutualCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        medicamentQuantityCol.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        medicamentTableItems =
                FXCollections.observableArrayList(
                        new ArrayList<PurchaseItem>()
                );


        medicamentTable.setItems(medicamentTableItems);
        medicamentTable.refresh();
    }

    private void calculateDisplayTotalPriceWithMutual() {
        float totalPriceWithMutual = 0.0f;
        for(PurchaseItem purchaseItem : medicamentTableItems){
            totalPriceWithMutual += ( purchaseItem.getTotalPrice()  * (100 - healthMutualRate) ) /100;
        }
        purchaseTotalPriceWithMutualText.setText(totalPriceWithMutual+" €");
    }

}
