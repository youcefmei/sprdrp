package com.youcefmei.sparadrap.controller;


import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    private TextField patientSearchTextField,patientLastNameRegisterTextField,patientFirstNameRegisterTextField,patientMailRegisterTextField;

    @FXML
    private TableView medicamentTable;
//
    @FXML
    private TableColumn<Medicament, String> medicamentTitleCol  ;

    @FXML
    private TableColumn<Medicament, Float> medicamentPriceCol,medicamentTotalPriceCol,medicamentTotalPriceWithMutualCol;

    @FXML
    private TableColumn<Medicament, Integer> medicamentQuantityCol;

    private Pharmacy pharmacy = Pharmacy.getInstance();
    private Prescription prescription;

    private Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un medicament");
    private FilteredList<Medicament> filteredMedicaments;
    private ObservableList<Medicament> medicamentTableItems;
    private ObservableList<Medicament> medicamentComboItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Patient combo init
        ObservableList<Patient> patientComboItems =
                FXCollections.observableArrayList(
                        pharmacy.getPatients()
                );
        patientNameSecuCombo.setItems(patientComboItems);
//        patientNameSecuCombo.getSelectionModel().selectFirst();

        // Doctor combo init
        ObservableList<Doctor> doctorComboItems =
                FXCollections.observableArrayList(
                        pharmacy.getDoctors()
                );
        doctorNameRegistrationNbCombo.setItems(doctorComboItems);
//        doctorNameRegistrationNbCombo.getSelectionModel().selectFirst();

        // Medicament init
            // combo init
        ObservableList<Medicament> medicamentComboItems =
                FXCollections.observableArrayList(
                        pharmacy.getMedicaments()
                );
        medicamentNameCombo.setItems(medicamentComboItems);
//        medicamentNameCombo.getSelectionModel().selectFirst();
            // spinner init
        medicamentQuantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        50
                )
        );

        // Pharmacy purchase init
        try {
            Purchase purchase = new Purchase();
            pharmacy.setCurrentPurchase(purchase);
        } catch (InvalidDateException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
    }


    @FXML
    private void handleSearchPatient(KeyEvent event) {
    }

//
    @FXML
    private void handleFilterSelectedPatient(ActionEvent event) {
    }

    @FXML
    private void handleRegisterPurchase(ActionEvent event) {
    }


    @FXML
    private void handleSearchDoctor(KeyEvent event) {
    }
    @FXML
    private void handleClearPurchase(ActionEvent event) {
    }

    @FXML
    private void handleFilterSelectedDoctor(ActionEvent event) {
    }

    @FXML
    private void handleSearchMedicament(KeyEvent event) {
    }

    @FXML
    private void handleFilterSelectedMedicament(ActionEvent event) {
    }

    @FXML
    private void handleAddMedicament(ActionEvent event) {
    }


//
    @FXML
    private void handleDeleteMedicament(ActionEvent event) {

    }
}
