package com.youcefmei.sparadrap.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Dashboard controller.
 */
public class DashboardController implements Initializable {

    @FXML
    private Button dashboardPatientButton,dashboardWithPrescriptionButton,dashboardWithoutPrescriptionButton,
                    dashboardDoctorButton,dashboardPurchaseHistoryButton;

    @FXML
    private AnchorPane anchorPanePatient, anchorPaneWithPrescription,anchorPaneWithoutPrescription,anchorPaneDoctor,
            anchorPanePurchaseHistory,mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        if(event.getSource()==dashboardPatientButton){
            changeMenuButtonStyleClass(dashboardPatientButton,"selected");
            anchorPanePatient.setVisible(true);
            anchorPaneWithPrescription.setVisible(false);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneDoctor.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardWithPrescriptionButton){
            changeMenuButtonStyleClass(dashboardWithPrescriptionButton,"selected");
            anchorPaneWithPrescription.setVisible(true);
            anchorPanePatient.setVisible(false);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneDoctor.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardWithoutPrescriptionButton){
            changeMenuButtonStyleClass(dashboardWithoutPrescriptionButton,"selected");
            anchorPaneWithoutPrescription.setVisible(true);
            anchorPaneWithPrescription.setVisible(false);
            anchorPanePatient.setVisible(false);
            anchorPaneDoctor.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardDoctorButton){
            changeMenuButtonStyleClass(dashboardDoctorButton,"selected");
            anchorPaneDoctor.setVisible(true);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneWithPrescription.setVisible(false);
            anchorPanePatient.setVisible(false);
            anchorPanePurchaseHistory.setVisible(false);
        }
        else if (event.getSource()==dashboardPurchaseHistoryButton){
            changeMenuButtonStyleClass(dashboardPurchaseHistoryButton,"selected");
            anchorPanePurchaseHistory.setVisible(true);
            anchorPaneDoctor.setVisible(false);
            anchorPaneWithoutPrescription.setVisible(false);
            anchorPaneWithPrescription.setVisible(false);
            anchorPanePatient.setVisible(false);
        }

    }



    private void changeMenuButtonStyleClass(Button button, String styleClass){
        List<Button> buttonsSelected = mainPane.lookupAll("." + styleClass).stream().filter(
                node -> node instanceof Button
        ).map(
                n -> (Button) n
        ).toList() ;
        for (Button btn : buttonsSelected){
            btn.getStyleClass().remove(styleClass);
        }
        button.getStyleClass().add(styleClass);
    }

}
