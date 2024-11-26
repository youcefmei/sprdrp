package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * The type Medicament.
 */
@Getter
public class Medicament {

    /**
     * The Categories.
     */

    private Integer medicamentId;
    private String title;
    @Setter
    private MedicamentCategory category;
    private float price;
    private  LocalDate startDate;
    @Setter
    private boolean needPrescription;

    /**
     * Instantiates a new Medicament.
     *
     * @param title            the title
     * @param category         the category
     * @param price            the price
     * @param startDate        the start date
     * @param needPrescription the need prescription
     * @throws InvalidInputException the invalid input exception
     * @throws InvalidDateException  the invalid date exception
     */



    public Medicament(Integer medicamentId,String title, MedicamentCategory category, float price,  LocalDate startDate, boolean needPrescription) throws InvalidInputException, InvalidDateException {
        setMedicamentId(medicamentId);
        setTitle(title);
        setCategory(category);
        setPrice(price);
        setStartDate(startDate);
        setNeedPrescription(needPrescription);
    }

    public Integer getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Integer medicamentId) {
        this.medicamentId = medicamentId;
    }



    /**
     * Sets title.
     *
     * @param title the title
     * @throws InvalidInputException the invalid input exception
     */
    public void setTitle(String title) throws InvalidInputException {

        if ( (title != null) && title.trim().matches("^[\\da-zA-Z\\séèïçôûàäâê&]{2,}$") ) {
            this.title = title;
        }
        else{
            throw new InvalidInputException("Le nom de médicament n'est pas valable");
        }
    }

    /**
     * Sets price.
     *
     * @param price the price
     * @throws InvalidInputException the invalid input exception
     */
    public void setPrice(float price) throws InvalidInputException {
        if (price > 0){
            this.price = price;
        }else{
            throw  new InvalidInputException("Le prix n'est pas valide");
        }
    }

//    /**
//     * Sets quantity.
//     *
//     * @param quantity the quantity
//     * @throws InvalidInputException the invalid input exception
//     */
//    public void setQuantity(int quantity) throws InvalidInputException {
//        if (quantity >= 0){
//            this.quantity = quantity;
//        }else{
//            throw  new InvalidInputException("La quantité n'est pas valide");
//        }
//    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     * @throws InvalidDateException the invalid date exception
     */
    public void setStartDate(LocalDate startDate) throws InvalidDateException {
        if (startDate == null) {
            throw new InvalidDateException("La date de mise en service ne peut etre null");

        } else if (startDate.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date de mise en service doit etre antérieur à aujourd'hui");
        }else{
            this.startDate = startDate;
        }
    }

    @Override
    public String toString() {
        if (needPrescription){
            return title + " - " + category ;
        } else{
            return title + " - " + category ;
        }

    }
}
