package com.youcefmei.sparadrap.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class PrescriptionLine {

    @Getter @Setter
    private Integer prescriptionLineId;

    @Getter
    @Setter
    @Positive(message = "La quantité doit être supérieur à zéro")
    private int quantity;

    @Getter
    @Setter
    @NotNull(message = "Le médicament ne peut pas être nul")
    private Medicament medicament;

    public PrescriptionLine(Integer prescriptionLineId, int quantity, Medicament medicament) {
        setPrescriptionLineId(prescriptionLineId);
        setQuantity(quantity);
        setMedicament(medicament);
    }

}
