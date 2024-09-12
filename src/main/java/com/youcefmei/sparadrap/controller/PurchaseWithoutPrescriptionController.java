package com.youcefmei.sparadrap.controller;


import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Medicament;
import com.youcefmei.sparadrap.model.Purchase;
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

    private Purchase purchase;
    private final Pharmacy pharmacy = Pharmacy.getInstance();

    private final Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private final Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un medicament");
    private FilteredList<Medicament> filteredMedicaments;

    //
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // combo init
//        medicamentComboItems =
//                FXCollections.observableArrayList(
//                        pharmacy.getMedicaments()
//                );

        medicamentNameCombo.setItems(pharmacy.getMedicaments());
        medicamentNameCombo.getSelectionModel().selectFirst();
        // spinner init
        medicamentQuantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        50
                )
        );
        try {
            purchase = new Purchase();
//            pharmacy.setCurrentPurchase(purchase);
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
        purchase.getMedicaments().clear();
        purchaseTotalPriceText.setText( purchase.getTotalAmountWithoutMutual() + " €");
        populateMedicamentTable();
    }

    @FXML
    private void handleRegisterPurchase(ActionEvent event)  {
        try {
            purchase.setPaid(true);
            pharmacy.addPurchase(purchase);
            alertInfo.setContentText("L'achat a bien été enregistré: "
                    + purchase.getTotalAmountWithoutMutual()
                    + "€\nId: " + purchase.getId()
                    + "\nDate: " + purchase.getDatetimeStr()
            );
            alertInfo.showAndWait();
            purchase = new Purchase();
            pharmacy.setCurrentPurchase( purchase);
            handleClearPurchase(null);
        } catch ( PaymentException | DuplicateException| InvalidInputException | InvalidDateException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
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
            purchase.addMedicament(medicament);
        } catch (InvalidInputException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
        for (Medicament med : purchase.getMedicaments()) {
            System.out.println(med + " - " + med.getQuantity() + "\n");
        }
        populateMedicamentTable();

        purchaseTotalPriceText.setText(purchase.getTotalAmountWithoutMutual() + " €");
    }

    @FXML
    private void handleDeleteMedicament(ActionEvent event) {
        System.out.println(purchase.getMedicaments());
        if (medicamentTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    purchase.removeMedicament((Medicament) medicamentTable.getSelectionModel().getSelectedItem());
                    purchaseTotalPriceText.setText(purchase.getTotalAmountWithoutMutual() + " €");
                    populateMedicamentTable();
                }
            });
        }
        System.out.println(purchase.getMedicaments());

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
        medicamentTable.setItems(purchase.getMedicaments());
        medicamentTable.refresh();
    }
}
