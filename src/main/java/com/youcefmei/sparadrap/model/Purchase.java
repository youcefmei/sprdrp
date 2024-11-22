package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.dao.MedicamentDAO;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.UUID;

/**
 * The type Purchase.
 */
public class Purchase {

    private Integer purchaseId;
    @Setter
    @NotNull
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}$",
            message = "L'identifiant est invalide"
    )
    private String ref;
    private LocalDateTime datetime;
    private String datetimeStr;
    private ObservableList<PurchaseItem> purchaseItems = FXCollections.observableArrayList();
    private boolean isPaid;
    private Prescription prescription;
    @Setter
    @PositiveOrZero(message = "Le prix ne peut pas etre inférieur à zero")
    private Float totalAmountWithMutual;
    @Setter
    @PositiveOrZero(message = "Le prix ne peut pas etre inférieur à zero")
    private Float totalAmountWithoutMutual;

    /**
     * Instantiates a new Purchase.
     *
     * @throws InvalidDateException the invalid date exception
     */

    public Purchase (Integer purchaseId) throws InvalidDateException {
        setPurchaseId(purchaseId);
    }

    /**
     * Instantiates a new Purchase.
     *
     * @param datetime the datetime
     * @throws InvalidDateException the invalid date exception
     */


    public Purchase(Integer purchaseId,LocalDateTime datetime) throws  InvalidDateException {
        this(purchaseId);
        setDatetime(datetime);
    }

    /**
     * Instantiates a new Purchase.
     *
     * @param prescription the prescription
     * @throws InvalidDateException  the invalid date exception
     * @throws InvalidInputException the invalid input exception
     */

    public Purchase(Integer purchaseId,Prescription prescription) throws  InvalidDateException, InvalidInputException {
        this(purchaseId,LocalDateTime.now());
        setPrescription(prescription);
    }


    /**
     * Instantiates a new Purchase.
     *
     * @param datetime     the datetime
     * @param prescription the prescription
     * @throws InvalidDateException  the invalid date exception
     * @throws InvalidInputException the invalid input exception
     */

    public Purchase(Integer purchaseId,LocalDateTime datetime,Prescription prescription) throws  InvalidDateException, InvalidInputException {
        this(purchaseId,datetime);
        setPrescription(prescription);
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * Is paid boolean.
     *
     * @return the boolean
     */
    public boolean isPaid() {
        return isPaid;
    }

    /**
     * Gets datetime.
     *
     * @return the datetime
     */
    public LocalDateTime getDatetime() {
        return datetime;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getRef() {
        return ref;
    }

    /**
     * Gets medicaments.
     *
     * @return the medicaments
     */
    public ObservableList<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }


    /**
     * Gets prescription.
     *
     * @return the prescription
     */
    public Prescription getPrescription() {
        return prescription;
    }


    /**
     * Gets total amount with mutual.
     *
     * @return the total amount with mutual
     */
    public float getTotalAmountWithMutual() {

        if ( totalAmountWithMutual != null ) {
            return totalAmountWithMutual;
        }
        else{
            float totalPrice = 0;
            if ( prescription == null ||  (prescription.getPatient().getHealthMutual() == null) ) {
                for ( PurchaseItem purchaseItem : purchaseItems) {
                    totalPrice += purchaseItem.getTotalPrice();
                }
            } else{
                HealthMutual healthMutual = prescription.getPatient().getHealthMutual();
                float rate =  healthMutual.getHealthCareRate();
                for (PurchaseItem purchaseItem : purchaseItems) {
                    totalPrice += purchaseItem.getTotalPrice() * ( ( 100 - rate )/100 );
                }
            }
            return totalPrice;

        }
    }


    /**
     * Get total amount without mutual.
     *
     * @return the float
     */
    public float getTotalAmountWithoutMutual(){
        if ( totalAmountWithoutMutual != null ) {
            return totalAmountWithoutMutual;
        }
        else{
            float totalPrice = 0;
            for (PurchaseItem purchaseItem : purchaseItems) {
                    totalPrice += purchaseItem.getTotalPrice();
            }
            return totalPrice;
        }
    }


    /**
     * Gets datetime "French" formatted .
     *
     * @return the datetime str
     */
    public String getDatetimeStr() {
        return datetimeStr;
    }

    /**
     * Sets datetime.
     *
     * @param datetime the datetime
     * @throws InvalidDateException the invalid date exception
     */
    public void setDatetime(@NotNull LocalDateTime datetime) throws InvalidDateException {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(1);

        if ( datetime.isAfter(now)) {

            throw new InvalidDateException("La date de facturation ne peut etre postérieur à aujourd'hui : " + now + " < " + datetime);
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            System.out.println(datetime);
            this.datetime = datetime;
            datetimeStr = datetime.format(formatter);
        }
    }


    /**
     * Add medicament.
     *
     * @param purchaseItem the purchase item
     * @throws InvalidInputException the invalid input exception
     */
    public void addPurchaseItem(@NotNull(message = "Le medicament ne peut etre nul") PurchaseItem purchaseItem) throws  InvalidInputException {

        if ( (prescription == null)  && purchaseItem.getMedicament().isNeedPrescription() ) {
            throw new InvalidInputException( "Ce medicament a besoin d'une ordonnance" );
        } else {
            // Check if medicament is in the list with same qty
            List<PurchaseItem> purchaseItemSFoundSameQty = purchaseItems.stream().filter(
                    purchaseItemTemp -> purchaseItemTemp.getMedicament().getTitle().equals( purchaseItem.getMedicament().getTitle() ) && ( purchaseItemTemp.getQuantity() == purchaseItem.getQuantity() )
            ).toList();

            List<PurchaseItem> purchaseItemsFound = purchaseItems.stream().filter(
                    purchaseItemTemp -> purchaseItemTemp.getMedicament().getTitle().equals( purchaseItem.getMedicament().getTitle()  )
            ).toList();

//            if ( !medicamentFound.isEmpty() ) {
////                throw new InvalidInputException("Déja dans le panier, vous pouvez changer la quantité\n pour modifier la commande");
////
//            } else {
            if ( purchaseItemSFoundSameQty.isEmpty() ) {
                if  ( !purchaseItemsFound.isEmpty() ){
                    purchaseItems.forEach(
                            purchaseItemTemp -> {
                                if ( purchaseItemTemp.getMedicament().getTitle().equals(purchaseItem.getMedicament().getTitle())   ) {
                                    purchaseItemTemp.setQuantity( purchaseItem.getQuantity() );
                                    purchaseItemTemp.setUnitPrice(purchaseItem.getUnitPrice());
                                }
                            }
                    );
                }
                // Add if not in the list
                else if ( prescription != null ) {
                    // With prescription add
                    List<PrescriptionLine> prescriptionLineFoundInPrescription = prescription.getPrescriptionLines().stream().filter(
                            prescriptionLineTemp -> prescriptionLineTemp.getMedicament().getTitle().equals(purchaseItem.getMedicament().getTitle())
                    ).toList();

                    if (!prescriptionLineFoundInPrescription.isEmpty()) {
                        purchaseItems.add( purchaseItem);
                    } else {
                        throw new InvalidInputException("Ce medicament n'est pas dans l'ordonnance");
                    }
                } else{
                    // Without prescription add
                   purchaseItems.add(purchaseItem);
                }
            }
        }
        System.out.println("Purchase amount: " + getTotalAmountWithoutMutual());
    }


    /**
     * Remove purchase item.
     *
     * @param purchaseItem the purchase item
     */
    public void removePurchaseItem(PurchaseItem purchaseItem){
        if ( purchaseItem != null ) {
            this.purchaseItems =  FXCollections.observableArrayList(
                purchaseItems.stream().filter(
                    purchaseItemTemp -> !purchaseItemTemp.getMedicament().getTitle().equals(
                            purchaseItem.getMedicament().getTitle()
                    )
                ).toList());

        }
    }

    /**
     * Remove purchase item.
     *
     * @param medicament the medicament
     */
    public void removePurchaseItem(Medicament medicament){
        if ( medicament != null ) {
            this.purchaseItems =  FXCollections.observableArrayList(
                    purchaseItems.stream().filter(
                            purchaseItemTemp -> !purchaseItemTemp.getMedicament().getTitle().equals(
                                    medicament.getTitle()
                            )
                    ).toList());

        }
    }
    /**
     * Sets medicaments.
     *
     * @param purchaseItems the medicaments
     * @throws InvalidInputException the invalid input exception
     */
    public void setPurchaseItems(List<PurchaseItem> purchaseItems) throws  InvalidInputException {
        if ( purchaseItems == null){
            throw new InvalidInputException("La liste de médicament ne peut etre null");
        } else if ( purchaseItems.isEmpty() ) {
            throw new InvalidInputException("La liste de médicament ne peut etre vide");
        } else if ( prescription != null ) {

            throw new InvalidInputException("La liste de médicament ne peut etre modifier car il s'agit d'un achat avec ordonnance");
        }else{
            this.purchaseItems.clear();
            for(PurchaseItem purchaseItem  : purchaseItems){
                System.out.println(purchaseItems);
                System.out.println(purchaseItem);
                this.addPurchaseItem(purchaseItem);
            }
        }
    }

    /**
     * Sets prescription.
     *
     * @param prescription the prescription
     * @throws InvalidInputException the invalid input exception
     */
    public void setPrescription(Prescription prescription) throws  InvalidInputException {
//        MedicamentDAO medicamentDAO = new MedicamentDAO();
        if (prescription == null){
            throw new InvalidInputException("L'ordonnance ne peut pas etre null");
        } else if (  prescription.getPrescriptionLines().isEmpty() ) {
            throw new InvalidInputException("Cette ordonnance ne contient pas de médicaments");
        } else if ( this.prescription == null && !getPurchaseItems().isEmpty() ){
            throw new InvalidInputException("Il est impossible d'ajouter une ordonnance si un achat sans ordonnance est en cours");
        } else{
            this.prescription = prescription;
            PurchaseItem purchaseItem = null;
            for (PrescriptionLine prescriptionLine : prescription.getPrescriptionLines()) {
//                Stock stock = medicamentDAO.findStockByMedicament(prescriptionLine.getMedicament());
                purchaseItem = new PurchaseItem(null,prescriptionLine.getQuantity(), prescriptionLine.getMedicament() );
                addPurchaseItem(purchaseItem);
            }
        }
    }

    /**
     * Sets paid.
     *
     * @param paid the paid
     * @throws InvalidInputException the invalid input exception
     */
    public void setPaid(boolean paid) throws InvalidInputException {
        if ( paid && purchaseItems.isEmpty()) {
            throw new InvalidInputException("La liste de medicament est vide");
        }else{
            isPaid = paid;
        }
    }


    @Override
    public String toString() {
        String title ;

        if (prescription != null) {
            title = "Achat avec ordonnance - " + ref + " - " + getDatetimeStr();
        }else{
            title = "Achat sans ordonnance - " + ref + " - " + getDatetimeStr();
        }
        return title;
    }
}
