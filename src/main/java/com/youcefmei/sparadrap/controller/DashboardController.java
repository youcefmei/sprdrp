package com.youcefmei.sparadrap.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Button dashboardPatientButton,dashboardWithPrescriptionButton,dashboardWithoutPrescriptionButton,
                    dashboardDoctorButton,dashboardPurchaseHistoryButton;

    @FXML
    private AnchorPane anchorPanePatient, anchorPaneWithPrescription,anchorPaneWithoutPrescription,anchorPaneDoctor,
            anchorPanePurchaseHistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        if(event.getSource()==dashboardPatientButton){
            anchorPanePatient.setVisible(true);
            anchorPaneWithPrescription.setVisible(false);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneDoctor.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardWithPrescriptionButton){
            anchorPaneWithPrescription.setVisible(true);
            anchorPanePatient.setVisible(false);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneDoctor.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardWithoutPrescriptionButton){
            anchorPaneWithoutPrescription.setVisible(true);
            anchorPaneWithPrescription.setVisible(false);
            anchorPanePatient.setVisible(false);
            anchorPaneDoctor.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardDoctorButton){
            anchorPaneDoctor.setVisible(true);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneWithPrescription.setVisible(false);
            anchorPanePatient.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardPurchaseHistoryButton){
            anchorPanePurchaseHistory.setVisible(true);
            anchorPaneDoctor.setVisible(false);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneWithPrescription.setVisible(false);
            anchorPanePatient.setVisible(false);
        }

//        anchorPaneLibrarian.toFront();
    }



}
