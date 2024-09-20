package com.youcefmei.sparadrap.controller;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Doctor controller.
 */
public class DoctorController implements Initializable {

    @FXML
    private TextField doctorLastNameTextField,doctorFirstNameTextField,doctorMailTextField,doctorPhoneTextField,
            doctorAddressTextField,doctorCityTextField,doctorAreacodeTextField,doctorRegistrationNumTextField;

    @FXML
    private RadioButton doctorSpecialistRadio,doctorGeneralRadio;

    @FXML
    private Group doctorToggleGroup;

    @FXML
    private Button doctorCancelEditButton;

    @FXML
    private TableView doctorTable;

    @FXML
    private TableColumn<Doctor, String> doctorRegistrationNbCol,doctorLastnameCol,doctorFirstnameCol,doctorSpecialityCol,doctorMailCol,doctorPhoneCol,doctorAddressCol,doctorAreaCodeCol,doctorCityCol;

    @FXML
    private ComboBox<String> doctorSpecialityCombo;

    @FXML
    private Accordion doctorAccordion;

    @FXML
    private TitledPane createOrUpdateDoctorTitledPane,listDoctorTitledPane;


    private Pharmacy pharmacy = Pharmacy.getInstance();
    private final Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private final Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un patient");

    private Doctor currentDoctor;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doctorSpecialityCombo.setItems(FXCollections.observableArrayList(
                DoctorSpecialized.SPECIALITIES
        ));
        doctorSpecialityCombo.getSelectionModel().selectFirst();
        initDoctorTable();

    }


    @FXML
    private void handleEditDoctor(ActionEvent event) {
        currentDoctor = (Doctor) doctorTable.getSelectionModel().getSelectedItem();

        if (currentDoctor != null) {
            createOrUpdateDoctorTitledPane.setText("Modifier");
            doctorCancelEditButton.setVisible(true);
            doctorAccordion.setExpandedPane(createOrUpdateDoctorTitledPane);
            listDoctorTitledPane.setVisible(false);

            doctorLastNameTextField.setText(currentDoctor.getLastName());
            doctorFirstNameTextField.setText(currentDoctor.getFirstName());
            doctorAddressTextField.setText(currentDoctor.getAddress());
            doctorCityTextField.setText(currentDoctor.getCity());
            doctorAreacodeTextField.setText(currentDoctor.getAreaCode());
            doctorRegistrationNumTextField.setText(currentDoctor.getRegistrationNb());
            doctorMailTextField.setText(currentDoctor.getMail());
            doctorPhoneTextField.setText(currentDoctor.getPhone());
            if (currentDoctor instanceof DoctorSpecialized) {
                doctorSpecialityCombo.setDisable(false);
                doctorSpecialistRadio.setSelected(true);
                doctorSpecialityCombo.getSelectionModel().select(((DoctorSpecialized) currentDoctor).getSpeciality());

            }else{
                doctorSpecialityCombo.setDisable(true);
                doctorGeneralRadio.setSelected(true);

            }
        }
    }

    @FXML
    private void handleDeleteDoctor(ActionEvent event) {

        if (doctorTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    Doctor doctor = pharmacy.getDoctors().get(doctorTable.getSelectionModel().getSelectedIndex());
//                    System.out.println(pharmacy.getPatients());
                    pharmacy.removeDoctor(doctor);
//                    pharmacy.getPatients().remove(patient);
                }
            });
        }

    }

    @FXML
    private void handleRegisterDoctor(ActionEvent event) {
        String confirmUpdateOrCreate = "";
        DoctorSpecialized doctorSpe = null;
        DoctorGeneral doctorGe = null;
        try {

            if (doctorSpecialistRadio.isSelected()) {

                doctorSpe = new DoctorSpecialized(
                        doctorFirstNameTextField.getText(),
                        doctorLastNameTextField.getText(),
                        doctorPhoneTextField.getText(),
                        doctorMailTextField.getText(),
                        doctorAddressTextField.getText(),
                        doctorCityTextField.getText(),
                        doctorAreacodeTextField.getText(),
                        doctorRegistrationNumTextField.getText(),
                        doctorSpecialityCombo.getValue()
                );

            } else{

                doctorGe = new DoctorGeneral(
                        doctorFirstNameTextField.getText(),
                        doctorLastNameTextField.getText(),
                        doctorPhoneTextField.getText(),
                        doctorMailTextField.getText(),
                        doctorAddressTextField.getText(),
                        doctorCityTextField.getText(),
                        doctorAreacodeTextField.getText(),
                        doctorRegistrationNumTextField.getText()
                );

            }
            if ( (currentDoctor != null) && (doctorSpe != null)) {
                pharmacy.removeDoctor(currentDoctor);
                confirmUpdateOrCreate = "Le docteur spécialiste a été modifié";
                pharmacy.addDoctorSpecialized(doctorSpe);
            }
            else if ( (currentDoctor != null) && (doctorGe != null)) {
                pharmacy.removeDoctor(currentDoctor);
                confirmUpdateOrCreate = "Le docteur généraliste a été modifié";
                pharmacy.addDoctorGeneral(doctorGe);
            }
            else if ( (currentDoctor == null) && (doctorGe != null)) {
                confirmUpdateOrCreate = "Le docteur généraliste a été crée";
                pharmacy.addDoctorGeneral(doctorGe);
            }
            else if ( (currentDoctor == null) && (doctorSpe != null)) {
                confirmUpdateOrCreate = "Le docteur spécialiste a été crée";
                pharmacy.addDoctorSpecialized(doctorSpe);
            }


            alertInfo.setContentText(confirmUpdateOrCreate);
            alertInfo.showAndWait();

            createOrUpdateDoctorTitledPane.setText("Créer");
            listDoctorTitledPane.setVisible(true);
            doctorAccordion.setExpandedPane(listDoctorTitledPane);
            clearInputs();
            doctorCancelEditButton.setVisible(false);
        } catch (InvalidInputException | DuplicateException  e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }


    }

    @FXML
    private void handleCancelEditDoctor(ActionEvent event) {
        currentDoctor = null;
        createOrUpdateDoctorTitledPane.setText("Créer");
        listDoctorTitledPane.setVisible(true);
        doctorAccordion.setExpandedPane(listDoctorTitledPane);

        clearInputs();
        doctorCancelEditButton.setVisible(false);
    }

    @FXML
    private void handleClearDoctor(ActionEvent event) {
        clearInputs();
    }

    @FXML
    private void handleSearchDoctor(ActionEvent event) {}

    @FXML
    private void handleDoctorType(ActionEvent event) {
        if (doctorSpecialistRadio.isSelected()) {
            doctorSpecialityCombo.setDisable(false);
        } else{
            doctorSpecialityCombo.setDisable(true);
        }
    }

    private void clearInputs() {
        doctorLastNameTextField.setText("");
        doctorFirstNameTextField.setText("");
        doctorAddressTextField.setText("");
        doctorCityTextField.setText("");
        doctorAreacodeTextField.setText("");
        doctorRegistrationNumTextField.setText("");
        doctorMailTextField.setText("");
        doctorPhoneTextField.setText("");
    }

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
