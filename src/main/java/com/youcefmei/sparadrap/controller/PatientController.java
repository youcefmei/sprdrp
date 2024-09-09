package com.youcefmei.sparadrap.controller;


import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PatientController implements Initializable {

    @FXML
    private Accordion patientAccordionPane;

    @FXML
    private TitledPane createOrUpdatePatientTitledPane, listPatientTitledPane ;

    @FXML
    private TextField patientSearchTextField,patientLastNameRegisterTextField,patientFirstNameRegisterTextField,patientMailRegisterTextField;

    @FXML
    private TableView patientTable;
//
    @FXML
    private TableColumn<Patient, String> patientSecuNumCol, patientMailCol,patientFirstnameCol,patientLastnameCol,patientBirthdateCol;
//
    private Pharmacy pharmacy = Pharmacy.getInstance();;

    private List<Patient> patients = pharmacy.getPatients();


//    private Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
//    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un utilisateur");
//
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPatientTable();
        populatePatientTable();
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
//        patientFirstNameRegisterTextField.clear();
//        patientLastNameRegisterTextField.clear();
//        patientMailRegisterTextField.clear();
//        populatePatientTable();
    }
//
    @FXML
    private void handleRegisterPatient(ActionEvent event) {
//        System.out.println(library.getCustomers());
//        String patientFirstName = patientFirstNameRegisterTextField.getText();
//        String patientLastName = patientLastNameRegisterTextField.getText();
//        String patientMail = patientMailRegisterTextField.getText();
//
//        try {
//            Customer customer = new Customer(patientFirstName,patientLastName,patientMail);
//            try {
//                library.addCustomer(customer);
//            } catch (DuplicateException e) {
//                Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
//                alert.showAndWait();
//            }
//            populatePatientTable();
//
//        } catch (InvalidInputException e) {
//            Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
//            alert.showAndWait();
//        }
//        populatePatientTable();
    }

    private void initPatientTable(){
        patientFirstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientLastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientMailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        patientBirthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        patientSecuNumCol.setCellValueFactory(new PropertyValueFactory<>("secuId"));
        patientFirstnameCol.setEditable(true);
        patientLastnameCol.setEditable(true);
        patientBirthdateCol.setEditable(true);
        patientSecuNumCol.setEditable(true);
        patientTable.setEditable(true);
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
        ObservableList<Patient> customerTableItems =
                FXCollections.observableArrayList(
                        patients
                );
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
        patientTable.setItems(customerTableItems);
//        patientTable.setItems(filteredCustomers);
    }
//
//
    @FXML
    private void handleDeletePatient(ActionEvent event) {
//        System.out.println(library.getCustomers());
//        if (patientTable.getSelectionModel().getSelectedItem() == null){
//            alertInfo.showAndWait();
//        }
//        else{
//            alertDelete.showAndWait().ifPresent(response -> {
//                if (response == ButtonType.OK){
//                    customers.remove(patientTable.getSelectionModel().getSelectedItem());
//                    populatePatientTable();
//                }
//            });
//        }
//        System.out.println(library.getCustomers());
    }

    @FXML
    private void handleEditPatient(ActionEvent event) {
        if (patientTable.getSelectionModel().getSelectedItem() != null){
            createOrUpdatePatientTitledPane.setText("Editer");
            patientAccordionPane.setExpandedPane(createOrUpdatePatientTitledPane);
        }
        else{

        }
    }
}
