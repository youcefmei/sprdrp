package com.youcefmei.sparadrap.controller;

import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Medicament;
import com.youcefmei.sparadrap.model.Prescription;
import com.youcefmei.sparadrap.model.Purchase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PurchaseHistoryController implements Initializable {

    @FXML
    private TableView purchaseHistoryTable;

    @FXML
    private TableColumn<Purchase,String> purchaseIdCol,patientSecuNumCol,patientLastnameCol,priceCol,priceWithMutualCol,purchaseDateCol,doctorRegistrationNbCol,doctorLastnameCol;

    @FXML
    private DatePicker purchaseDatePicker;

    private Pharmacy pharmacy = Pharmacy.getInstance();

    private FilteredList<Purchase> purchaseInDate  = new FilteredList<>(pharmacy.getPurchases());

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        purchaseDatePicker.setValue(LocalDate.now());
        handleDatePicker(new ActionEvent());
    }

    private void initTable() {
        priceCol.setCellValueFactory(new PropertyValueFactory<Purchase,String>("totalAmountWithoutMutual"));
        priceWithMutualCol.setCellValueFactory(new PropertyValueFactory<Purchase,String>("totalAmountWithMutual"));
        purchaseDateCol.setCellValueFactory(new PropertyValueFactory<Purchase,String>("datetimeStr"));
        purchaseIdCol.setCellValueFactory(new PropertyValueFactory<Purchase,String>("id"));

        patientSecuNumCol.setCellValueFactory(cellData -> {
            Prescription prescription = cellData.getValue().getPrescription();
            if ( prescription != null ){
                return new SimpleStringProperty(prescription.getPatient().getSecuId());
            } else {
                return new SimpleStringProperty(null);
            }
        });

        patientLastnameCol.setCellValueFactory(cellData -> {
            Prescription prescription = cellData.getValue().getPrescription();
            if ( prescription != null ){
                return new SimpleStringProperty(prescription.getPatient().getLastName());
            } else {
                return new SimpleStringProperty(null);
            }
        });

        doctorLastnameCol.setCellValueFactory(cellData -> {
            Prescription prescription = cellData.getValue().getPrescription();
            if ( prescription != null ){
                return new SimpleStringProperty(prescription.getDoctor().getLastName());
            } else {
                return new SimpleStringProperty(null);
            }
        });

        doctorRegistrationNbCol.setCellValueFactory(cellData -> {
            Prescription prescription = cellData.getValue().getPrescription();
            if ( prescription != null ){
                return new SimpleStringProperty(prescription.getDoctor().getRegistrationNb());
            } else {
                return new SimpleStringProperty(null);
            }
        });

        purchaseHistoryTable.setItems(pharmacy.getPurchases());
    }

    @FXML
    public void handleDatePicker(ActionEvent event) {

        purchaseInDate.setPredicate(
                new Predicate<Purchase>() {
                    @Override
                    public boolean test(Purchase purchase) {
                        return  formatter.format(purchase.getDatetime()).equals(formatter.format(purchaseDatePicker.getValue()));
                    }
                }
        );
        purchaseHistoryTable.setItems(purchaseInDate);
    }

    @FXML
    private void handleEditPurchase(ActionEvent event) {
//        initTable();
    }


}

