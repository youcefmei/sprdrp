package com.youcefmei.sparadrap.controller;

import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Doctor;
import com.youcefmei.sparadrap.model.DoctorSpecialized;
import com.youcefmei.sparadrap.model.Prescription;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorController implements Initializable {


    @FXML
    private TableView doctorTable;

    @FXML
    private TableColumn<Doctor, String> doctorRegistrationNbCol,doctorLastnameCol,doctorFirstnameCol,doctorSpecialityCol,doctorMailCol,doctorPhoneCol,doctorAddressCol,doctorAreaCodeCol,doctorCityCol;

    private Pharmacy pharmacy = Pharmacy.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initDoctorTable();

    }


    @FXML
    private void handleEditDoctor(ActionEvent event) {

    }

    @FXML
    private void handleDeleteDoctor(ActionEvent event) {}

    @FXML
    private void handleRegisterDoctor(ActionEvent event) {}

    @FXML
    private void handleCancelEditDoctor(ActionEvent event) {}

    @FXML
    private void handleClearDoctor(ActionEvent event) {}

    @FXML
    private void handleSearchDoctor(ActionEvent event) {}

    @FXML
    private void handleDoctorType(ActionEvent event) {}


    private void initDoctorTable() {
        doctorFirstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        doctorLastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        doctorMailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        doctorPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        doctorRegistrationNbCol.setCellValueFactory(new PropertyValueFactory<>("registrationNb"));
        doctorAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        doctorCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        doctorAreaCodeCol.setCellValueFactory(new PropertyValueFactory<>("areaCode"));
        doctorSpecialityCol.setCellValueFactory(cellData -> {
//            Prescription prescription = cellData.getValue().getPrescription();
            if ( cellData.getValue() instanceof DoctorSpecialized){
                return new SimpleStringProperty( ( (DoctorSpecialized)cellData.getValue() ).getSpeciality()) ;
            } else {
                return new SimpleStringProperty(null);
            }
        });

//        doctorFirstnameCol.setEditable(true);
//        doctorLastnameCol.setEditable(true);
//        doctorTable.setEditable(true);
        doctorTable.setItems(pharmacy.getDoctors());

    }

//    @FXML
//    private void handleFilterSelectedDoctor(ActionEvent event) {}

}
