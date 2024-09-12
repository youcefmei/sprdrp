package com.youcefmei.sparadrap.controller;


import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Doctor;
import com.youcefmei.sparadrap.model.DoctorGeneral;
import com.youcefmei.sparadrap.model.HealthMutual;
import com.youcefmei.sparadrap.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    private Accordion patientAccordionPane;

    @FXML
    private TitledPane createOrUpdatePatientTitledPane, listPatientTitledPane ;

    @FXML
    private TextField patientLastNameTextField,patientFirstNameTextField,patientMailTextField,patientPhoneTextField,
            patientAddressTextField,patientCityTextField,patientAreacodeTextField,patientSecuNumTextField;

//    @FXML
    private DatePicker patientBirthDatePicker;

    @FXML
    private Pane birthDatePane;

    @FXML
    private ComboBox<DoctorGeneral> familyDoctorCombo;

    @FXML
    private ComboBox<HealthMutual> healthMutualCombo;

    @FXML
    private Button patientCancelEditButton;

    @FXML
    private TableView patientTable;

    @FXML
    private TableColumn<Patient, String> patientSecuNumCol, patientMailCol,patientFirstnameCol,patientLastnameCol,patientBirthdateCol;

    private final Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private final Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un patient");

    private Pharmacy pharmacy = Pharmacy.getInstance();;

    private Patient currentPatient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPatientTable();
//        populatePatientTable();
        patientBirthDatePicker = new DatePicker();
        birthDatePane.getChildren().add(patientBirthDatePicker);
        patientCancelEditButton.setVisible(false);

        healthMutualCombo.setItems(pharmacy.getHealthMutuals());
        familyDoctorCombo.setItems(  pharmacy.getDoctorGenerals() );
    }


    @FXML
    private void handleSearchPatient(KeyEvent event) {
//        System.out.println(library.getCustomers());
//        populatePatientTable();
    }
//

    @FXML
    private void handleFilterSelectedPatient(ActionEvent event) {
//        populatePatientTable();
    }

    @FXML
    private void handleClearPatient(ActionEvent event) {
        clearInputs();
    }
//
    @FXML
    private void handleRegisterPatient(ActionEvent event) {
        Patient patient;
        String confirmUpdateOrCreate;
        try {
            patient = new Patient(
                    patientFirstNameTextField.getText(),
                    patientLastNameTextField.getText(),
                    patientPhoneTextField.getText(),
                    patientMailTextField.getText(),
                    patientAddressTextField.getText(),
                    patientCityTextField.getText(),
                    patientAreacodeTextField.getText(),
                    patientSecuNumTextField.getText(),
                    patientBirthDatePicker.getValue(),
                    (DoctorGeneral) familyDoctorCombo.getSelectionModel().getSelectedItem(),
                    healthMutualCombo.getValue()
            );
            if ( currentPatient != null) {
                pharmacy.removePatient(currentPatient);
                confirmUpdateOrCreate = "Le patient a été modifié";
            }else{
                confirmUpdateOrCreate = "Le patient a été ajouté";
            }

            pharmacy.addPatient(patient);
            alertInfo.setContentText(confirmUpdateOrCreate);
            alertInfo.showAndWait();

            createOrUpdatePatientTitledPane.setText("Créer");
            listPatientTitledPane.setVisible(true);
            patientAccordionPane.setExpandedPane(listPatientTitledPane);
            clearInputs();
            patientCancelEditButton.setVisible(false);
        } catch (InvalidInputException | InvalidDateException | DuplicateException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
    }

    private void initPatientTable(){
        patientFirstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientLastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientMailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        patientBirthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthDateStr"));
        patientSecuNumCol.setCellValueFactory(new PropertyValueFactory<>("secuId"));
        patientFirstnameCol.setEditable(true);
        patientLastnameCol.setEditable(true);
        patientBirthdateCol.setEditable(true);
        patientSecuNumCol.setEditable(true);
        patientTable.setEditable(true);
        patientTable.setItems(pharmacy.getPatients());
//
//        patientFirstnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        patientLastnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        patientMailCol.setCellFactory(TextFieldTableCell.forTableColumn());
//
//        patientFirstnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
//                try {
//                    ((Customer)event.getTableView().getItems().get(
//                            event.getTablePosition().getRow())).setFirstName(event.getNewValue());
//                } catch (InvalidInputException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        patientLastnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
//                try {
//                    ((Customer)event.getTableView().getItems().get(
//                            event.getTablePosition().getRow())).setLastName(event.getNewValue());
//                } catch (InvalidInputException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//
//        patientMailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
//                try {
//                    ((Customer)event.getTableView().getItems().get(
//                            event.getTablePosition().getRow())).setMail(event.getNewValue());
//                } catch (InvalidInputException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

    }
//
    private void populatePatientTable() {
//        patientTableItems =
//                FXCollections.observableArrayList(
//                        patients);
//        filteredCustomers = new FilteredList<>(customerTableItems);
//        String selectedItem = patientNameMailCombo.getSelectionModel().getSelectedItem();
//
//        filteredCustomers.setPredicate(
//                new Predicate<Customer>() {
//                    @Override
//                    public boolean test(Customer customer) {
//                        if ( selectedItem.equals("Nom")   ) {
//                            return customer.getLastName().toLowerCase().contains(patientSearchTextField.getText().toLowerCase());
//
//                        }else{
//                            return customer.getMail().toLowerCase().contains(patientSearchTextField.getText().toLowerCase());
//                        }
//                    }
//                }
//        );
//        patientTable.setItems(pharmacy.getPatients());
//        patientTable.setItems(filteredCustomers);
    }
//
//

    @FXML
    private void handleCancelEditPatient(ActionEvent event) {
        currentPatient = null;
        createOrUpdatePatientTitledPane.setText("Créer");
        listPatientTitledPane.setVisible(true);
        patientAccordionPane.setExpandedPane(listPatientTitledPane);

        clearInputs();
        patientCancelEditButton.setVisible(false);
    }

    private void clearInputs() {
        patientLastNameTextField.setText("");
        patientFirstNameTextField.setText("");
        patientAddressTextField.setText("");
        patientCityTextField.setText("");
        patientAreacodeTextField.setText("");
        patientSecuNumTextField.setText("");
        patientMailTextField.setText("");
        patientPhoneTextField.setText("");
        patientBirthDatePicker.setValue(null);
        familyDoctorCombo.getSelectionModel().clearSelection();
        healthMutualCombo.getSelectionModel().clearSelection();
    }


    @FXML
    private void handleDeletePatient(ActionEvent event) {

        if (patientTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    Patient patient = pharmacy.getPatients().get(patientTable.getSelectionModel().getSelectedIndex());
                    System.out.println(pharmacy.getPatients());
                    pharmacy.removePatient(patient);
                    pharmacy.getPatients().remove(patient);
                }
            });
        }
    }

    @FXML
    private void handleEditPatient(ActionEvent event) {
        currentPatient = (Patient) patientTable.getSelectionModel().getSelectedItem();
        if (currentPatient != null){
            createOrUpdatePatientTitledPane.setText("Modifier");
            patientCancelEditButton.setVisible(true);
            patientAccordionPane.setExpandedPane(createOrUpdatePatientTitledPane);
            listPatientTitledPane.setVisible(false);

            patientLastNameTextField.setText(currentPatient.getLastName());
            patientFirstNameTextField.setText(currentPatient.getFirstName());
            patientAddressTextField.setText(currentPatient.getAddress());
            patientCityTextField.setText(currentPatient.getCity());
            patientAreacodeTextField.setText(currentPatient.getAreaCode());
            patientSecuNumTextField.setText(currentPatient.getSecuId());
            patientMailTextField.setText(currentPatient.getMail());
            patientPhoneTextField.setText(currentPatient.getPhone());

//            doctorComboItems = FXCollections.observableArrayList(
//                            pharmacy.getDoctorGenerals()
//                    );
            familyDoctorCombo.getSelectionModel().select(currentPatient.getFamilyDoctor());


            healthMutualCombo.getSelectionModel().select(currentPatient.getHealthMutual());

            patientBirthDatePicker.setEditable(true);
            patientBirthDatePicker.setValue(currentPatient.getBirthDate());

            System.out.println(currentPatient.getBirthDate());

            System.out.println(patientBirthDatePicker.getValue());
        }
        else{
            alertInfo.showAndWait();
        }
    }
}
