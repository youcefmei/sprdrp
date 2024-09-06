package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Medicament {

    public final static String[] categories = {"Analgésiques","Antibiotiques","Antituberculeux","Antimycosiques",
            "Antiviraux","Antihistaminiques","Antipyrétiques","Antispasmodiques",
            "Cardiologie","Dermatologie","Endocrinologie","Gastro-entérologie","Hématologie",
            "Neurologie","Oncologie","Psychiatrie","Rhumatologie","Urologie"
    };
    private String title;
    private String category;
    private float price;
    private float totalPrice;
    private int quantity;
    private  LocalDate startDate;
    private boolean needPrescription;

    public Medicament(String title, String category, float price, int quantity, LocalDate startDate, boolean needPrescription) throws InvalidInputException, InvalidDateException {
        setTitle(title);
        setCategory(category);
        setPrice(price);
        setQuantity(quantity);
        setStartDate(startDate);
        setNeedPrescription(needPrescription);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isNeedPrescription() {
        return needPrescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getTotalPrice() {
        return price*quantity;
    }


    public void setTitle(String title) throws InvalidInputException {

        if ( (title != null) && title.trim().matches("^[\\da-zA-Z\\s]+$") ) {
            this.title = title;
        }
        else{
            throw new InvalidInputException("Le nom de médicament n'est pas valable");
        }
    }

    public void setPrice(float price) throws InvalidInputException {
        if (price >= 0){
            this.price = price;
        }else{
            throw  new InvalidInputException("Le prix n'est pas valide");
        }
    }

    public void setQuantity(int quantity) throws InvalidInputException {
        if (quantity >= 0){
            this.quantity = quantity;
        }else{
            throw  new InvalidInputException("La quantité n'est pas valide");
        }
    }

    public void setCategory(String category) throws InvalidInputException {
        List<String> categoryFounds = Arrays.asList(categories).stream().filter(
                specialityTemp -> specialityTemp.equals(category)
        ).toList();

        if (!categoryFounds.isEmpty()) {
            this.category = category;
        } else{
            throw new InvalidInputException("La catégorie n'est pas valide, veuillez choisir entre :" + categories );
        }
    }

    public void setNeedPrescription(boolean needPrescription) {
        this.needPrescription = needPrescription;
    }

    public void setStartDate(LocalDate startDate) throws InvalidDateException {
        if (startDate == null) {
            throw new InvalidDateException("La date de mise en service doit etre antérieur à aujourd'hui");

        } else if (startDate.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date de naissance doit etre antérieur à aujourd'hui");
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
