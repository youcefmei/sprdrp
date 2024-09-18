package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * The type Medicament.
 */
public class Medicament {

    /**
     * The Categories.
     */
    public final static String[] categories = {"Analgésiques","Antibiotiques","Antituberculeux","Antimycosiques",
            "Antiviraux","Antihistaminiques","Antipyrétiques","Antispasmodiques",
            "Cardiologie","Dermatologie","Endocrinologie","Gastro-entérologie","Hématologie",
            "Neurologie","Oncologie","Psychiatrie","Rhumatologie","Urologie"
    };

    private String title;
    private String category;
    private float price;
    private int quantity;
    private  LocalDate startDate;
    private boolean needPrescription;

    /**
     * Instantiates a new Medicament.
     *
     * @param title            the title
     * @param category         the category
     * @param price            the price
     * @param quantity         the quantity
     * @param startDate        the start date
     * @param needPrescription the need prescription
     * @throws InvalidInputException the invalid input exception
     * @throws InvalidDateException  the invalid date exception
     */
    public Medicament(String title, String category, float price, int quantity, LocalDate startDate, boolean needPrescription) throws InvalidInputException, InvalidDateException {
        setTitle(title);
        setCategory(category);
        setPrice(price);
        setQuantity(quantity);
        setStartDate(startDate);
        setNeedPrescription(needPrescription);
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Is need prescription.
     *
     * @return the boolean
     */
    public boolean isNeedPrescription() {
        return needPrescription;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public float getTotalPrice() {
        return price * quantity;
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

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     * @throws InvalidInputException the invalid input exception
     */
    public void setQuantity(int quantity) throws InvalidInputException {
        if (quantity >= 0){
            this.quantity = quantity;
        }else{
            throw  new InvalidInputException("La quantité n'est pas valide");
        }
    }

    /**
     * Sets category.
     *
     * @param category the category
     * @throws InvalidInputException the invalid input exception
     */
    public void setCategory(String category) throws InvalidInputException {
        List<String> categoryFounds = Arrays.asList(categories).stream().filter(
                specialityTemp -> specialityTemp.equals(category)
        ).toList();

        if (!categoryFounds.isEmpty()) {
            this.category = category;
        } else{
            throw new InvalidInputException("La catégorie n'est pas valide, veuillez choisir entre :" + Arrays.stream(categories).toList() );
        }
    }

    /**
     * Sets need prescription.
     *
     * @param needPrescription the need prescription
     */
    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

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
