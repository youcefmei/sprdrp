package com.youcefmei.sparadrap.controller;


import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Medicament;
import com.youcefmei.sparadrap.model.Purchase;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PurchaseWithoutPrescriptionController implements Initializable {

    @FXML
    private ComboBox<Medicament> medicamentNameCombo;

    @FXML
    private Spinner<Integer> medicamentQuantitySpinner;

    @FXML
    private Button addMedicamentButton;

    @FXML
    private Button deleteMedicamentButton;

    @FXML
    private TextField medicamentSearchTextField;

    @FXML
    private TableView medicamentTable;

    @FXML
    private Text purchaseTotalPriceText;

    //
    @FXML
    private TableColumn<Medicament, String> medicamentTitleCol  ;

    @FXML
    private TableColumn<Medicament, Float> medicamentPriceCol,medicamentTotalPriceCol;

    @FXML
    private TableColumn<Medicament, Integer> medicamentQuantityCol;

    private Pharmacy pharmacy = Pharmacy.getInstance();

    private Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un medicament");
    private FilteredList<Medicament> filteredMedicaments;
    private ObservableList<Medicament> medicamentTableItems;
    private ObservableList<Medicament> medicamentComboItems;

    //
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // combo init
        medicamentComboItems =
                FXCollections.observableArrayList(
                        pharmacy.getMedicaments()
                );

        medicamentNameCombo.setItems(medicamentComboItems);
        medicamentNameCombo.getSelectionModel().selectFirst();
        // spinner init
        medicamentQuantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        50
                )
        );

        try {
            Purchase purchase = new Purchase();
            pharmacy.setCurrentPurchase(purchase);
        } catch (InvalidDateException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }

        initMedicamentTable();
        populateMedicamentTable();
    }


    @FXML
    private void handleClearPurchase(ActionEvent event) throws InvalidInputException {
        medicamentSearchTextField.setText("");
        medicamentQuantitySpinner.getValueFactory().setValue(1);
        pharmacy.getCurrentPurchase().getMedicaments().clear();
        purchaseTotalPriceText.setText( pharmacy.getCurrentPurchase().getTotalAmount() + " €");
        populateMedicamentTable();
    }

    @FXML
    private void handleRegisterPurchase(ActionEvent event)  {
        try {
            pharmacy.getCurrentPurchase().setPaid(true);
            pharmacy.addCurrentPurchase();
            alertInfo.setContentText("L'achat a bien été enregistré: "
                    + pharmacy.getCurrentPurchase().getTotalAmount()
                    + "€\nId: " + pharmacy.getCurrentPurchase().getId()
                    + "\nLe: " + pharmacy.getCurrentPurchase().getDateStr()
            );
            alertInfo.showAndWait();
            pharmacy.setCurrentPurchase(new Purchase());
            handleClearPurchase(null);
        } catch ( PaymentException | DuplicateException| InvalidInputException | InvalidDateException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
    }

    @FXML
    private void handleSearchMedicament(KeyEvent event) {
        filteredMedicaments = new FilteredList<>(medicamentComboItems);
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

//    @FXML
//    private void handleFilterSelectedMedicament(ActionEvent event) {
//    }

    @FXML
    private void handleAddMedicament(ActionEvent event) {
        Medicament medicament = medicamentNameCombo.getSelectionModel().getSelectedItem();
        if (medicament == null){
            alertInfo.setContentText("Veuillez choisir un medicament");
            alertInfo.showAndWait();
        }

        try {
            medicament.setQuantity(medicamentQuantitySpinner.getValue());
            pharmacy.getCurrentPurchase().addMedicament(medicament);
        } catch (InvalidInputException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
        for (Medicament med : pharmacy.getCurrentPurchase().getMedicaments()) {
            System.out.println(med + " - " + med.getQuantity() + "\n");
        }
        populateMedicamentTable();

        purchaseTotalPriceText.setText(pharmacy.getCurrentPurchase().getTotalAmount() + " €");
    }

    @FXML
    private void handleDeleteMedicament(ActionEvent event) {
        System.out.println(pharmacy.getCurrentPurchase().getMedicaments());
        if (medicamentTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    pharmacy.getCurrentPurchase().removeMedicament((Medicament) medicamentTable.getSelectionModel().getSelectedItem());
                    purchaseTotalPriceText.setText(pharmacy.getCurrentPurchase().getTotalAmount() + " €");
                    populateMedicamentTable();
                }
            });
        }
        System.out.println(pharmacy.getCurrentPurchase().getMedicaments());

    }

    private void initMedicamentTable() {
        medicamentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        medicamentPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        medicamentTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        medicamentQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        medicamentTitleCol.setEditable(true);
        medicamentTotalPriceCol.setEditable(true);
        medicamentPriceCol.setEditable(true);
        medicamentQuantityCol.setEditable(true);

        medicamentTable.setEditable(true);

        medicamentTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        medicamentPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        medicamentTotalPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        medicamentQuantityCol.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }


    private void populateMedicamentTable() {
        medicamentTableItems =
                FXCollections.observableArrayList(
                        pharmacy.getCurrentPurchase().getMedicaments()
                );
        medicamentTable.setItems(medicamentTableItems);
        medicamentTable.refresh();
    }
}
