package com.youcefmei.sparadrap.model;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

public class PurchaseItem {

    private Integer id;
    @PositiveOrZero
    private int quantity;
    private Medicament medicament;
    @PositiveOrZero
    @Setter
    @Getter
    private Float unitPrice;

    public PurchaseItem(Integer id, int quantity, Medicament medicament) {
        setId(id);
        setQuantity(quantity);
        setMedicament(medicament);
        setUnitPrice( medicament.getPrice() );
    }

    public PurchaseItem(Integer id, int quantity, Medicament medicament, float unitPrice) {
        setId(id);
        setQuantity(quantity);
        setMedicament(medicament);
        setUnitPrice(unitPrice);
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

    public float getTotalPrice() {
          return quantity * medicament.getPrice();
    }


}
