package com.youcefmei.sparadrap.controller;


import com.youcefmei.sparadrap.dao.MedicamentDAO;
import com.youcefmei.sparadrap.dao.PurchaseDAO;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Medicament;
import com.youcefmei.sparadrap.model.Purchase;

import com.youcefmei.sparadrap.model.PurchaseItem;
import com.youcefmei.sparadrap.model.Stock;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
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

/**
 * The type Purchase without prescription controller.
 */
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
    private TableColumn<PurchaseItem, String> medicamentTitleCol  ;

    @FXML
    private TableColumn<PurchaseItem, Float> medicamentPriceCol,medicamentTotalPriceCol;

    @FXML
    private TableColumn<PurchaseItem, Integer> medicamentQuantityCol;

    private Purchase purchase;
    private final Pharmacy pharmacy = Pharmacy.getInstance();


    private final Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private final Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un medicament");
    private FilteredList<Medicament> filteredMedicaments;

//    private PurchaseDAO purchaseDAO = new PurchaseDAO();
//    private MedicamentDAO medicamentDAO = new MedicamentDAO();
    //
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // combo init
//        medicamentComboItems =
//                FXCollections.observableArrayList(
//                        pharmacy.getMedicaments()
//                );

        medicamentNameCombo.setItems(pharmacy.getMedicaments());
//        medicamentNameCombo.setItems(medicamentDAO.findAllObservable());
        medicamentNameCombo.getSelectionModel().selectFirst();
        // spinner init
        medicamentQuantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        50
                )
        );
        try {
            purchase = new Purchase(null);
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
        purchase.getPurchaseItems().clear();
        purchaseTotalPriceText.setText( purchase.getTotalAmountWithoutMutual() + " €");
        populateMedicamentTable();
    }

    @FXML
    private void handleRegisterPurchase(ActionEvent event)  {
        try {
            purchase.setPaid(true);
            purchase =  pharmacy.addPurchase(purchase);
//            Purchase purchaseTemp =  purchaseDAO.create(purchase);
//            Purchase purchaseTemp = purchaseDAO.findById(purchaseId);
//            purchase.setPurchaseId(purchaseTemp.getPurchaseId());
//            purchase.setRef(purchaseTemp.getRef());
            alertInfo.setContentText("L'achat a bien été enregistré: "
                    + purchase.getTotalAmountWithoutMutual()
                    + "€\nId: " + purchase.getRef()
                    + "\nDate: " + purchase.getDatetimeStr()
            );
            alertInfo.showAndWait();
            purchase = new Purchase(null);
//            pharmacy.setCurrentPurchase( purchase);
            handleClearPurchase(null);
        } catch ( InvalidInputException | InvalidDateException | DuplicateException | PaymentException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
    }

    @FXML
    private void handleSearchMedicament(KeyEvent event) {
        filteredMedicaments = new FilteredList<>( pharmacy.getMedicaments() );
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
            PurchaseItem purchaseItem = new PurchaseItem(
                    null,
                    medicamentQuantitySpinner.getValue(),
                    medicament
            );
            purchase.addPurchaseItem(purchaseItem);
        } catch (InvalidInputException e) {
            alertInfo.setContentText(e.getMessage());
            alertInfo.showAndWait();
        }
        for ( PurchaseItem purchaseItem : purchase.getPurchaseItems()) {
            System.out.println(purchaseItem.getMedicament() + " - " + purchaseItem.getQuantity() + "\n");
        }
        populateMedicamentTable();

        purchaseTotalPriceText.setText(purchase.getTotalAmountWithoutMutual() + " €");
    }

    @FXML
    private void handleDeleteMedicament(ActionEvent event) {
        if (medicamentTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    purchase.removePurchaseItem((PurchaseItem) medicamentTable.getSelectionModel().getSelectedItem());
                    purchaseTotalPriceText.setText(purchase.getTotalAmountWithoutMutual() + " €");
                    populateMedicamentTable();
                }
            });
        }
        System.out.println(purchase.getPurchaseItems());

    }

    private void initMedicamentTable() {
//        medicamentTitleCol.setCellValueFactory(new PropertyValueFactory<>("medicament"));

        medicamentTitleCol.setCellValueFactory(
                cellData -> new SimpleStringProperty( cellData.getValue().getMedicament().getTitle() )
        );


        medicamentPriceCol.setCellValueFactory(
                cellData ->  new SimpleFloatProperty(  cellData.getValue().getMedicament().getPrice() ).asObject()
        );

        medicamentTotalPriceCol.setCellValueFactory(
                cellData ->  new SimpleFloatProperty(  cellData.getValue().getTotalPrice() ).asObject()
        );

//        medicamentPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
//        medicamentTotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
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
        medicamentTable.setItems(purchase.getPurchaseItems());
        medicamentTable.refresh();
    }
}
