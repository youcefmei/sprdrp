package com.youcefmei.sparadrap.model;

import jakarta.validation.constraints.PositiveOrZero;

public class Stock {

    private Integer id;
    @PositiveOrZero
    private int quantity;
    private Medicament medicament;

    public Stock(Integer id, int quantity, Medicament medicament) {
        setId(id);
        setQuantity(quantity);
        setMedicament(medicament);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }
}
