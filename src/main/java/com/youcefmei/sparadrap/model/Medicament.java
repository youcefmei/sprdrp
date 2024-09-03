package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.List;

public class Medicament {

    private String name;
    private List<String> categories = List.of("Analgésiques","Antibiotiques","Antituberculeux","Antimycosiques","Antiviraux","Antihistaminiques","Antipyrétiques","Antispasmodiques","Cardiologie","Dermatologie","Endocrinologie","Gastro-entérologie","Hématologie","Neurologie","Oncologie","Psychiatrie","Rhumatologie","Urologie");
    private String category;
    private float price;
    private int quantity;
    private  LocalDate startDate;
    private boolean needPrescription;


    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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
        List<String> categoryFounds = categories.stream().filter(
                specialityTemp -> specialityTemp.equals(category)
        ).toList();

        if (!categoryFounds.isEmpty()) {
            this.category = category;
        } else{
            throw new InvalidInputException("La catégorie n'est pas valide");
        }

    }

}
