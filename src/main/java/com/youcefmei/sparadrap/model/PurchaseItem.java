package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import javax.print.attribute.standard.MediaSize;

@Getter
public class PurchaseItem {

    @Setter
    private Integer id;
    @Setter
    @PositiveOrZero
    private int quantity;
    @NotNull(message = "Le medicament ne peut pas etre nul") @Setter
    private Medicament medicament;
    @PositiveOrZero
    @Setter
    private Float unitPrice;

    public PurchaseItem(Integer id, int quantity, Medicament medicament) throws InvalidInputException {
        setId(id);
        setQuantity(quantity);
        setMedicament(medicament);
        setUnitPrice( medicament.getPrice() );
    }

    public PurchaseItem(Integer id, int quantity, Medicament medicament, float unitPrice) throws InvalidInputException {
        this(id, quantity, medicament);
        setUnitPrice(unitPrice);
    }



    public float getTotalPrice() {
          return quantity * getUnitPrice();
    }


}
