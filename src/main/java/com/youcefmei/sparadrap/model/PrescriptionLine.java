package com.youcefmei.sparadrap.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

public class PrescriptionLine {

    @Getter @Setter
    private Integer prescriptionId;

    @Getter
    @Setter
    @Positive(message = "La quantité doit être supérieur à zéro")
    private int quantity;

    @Getter
    @Setter
    @NotNull(message = "Le médicament ne peut pas être nul")
    private Medicament medicament;

    public PrescriptionLine(Integer prescriptionId, int quantity, Medicament medicament) {
        setPrescriptionId(prescriptionId);
        setQuantity(quantity);
        setMedicament(medicament);
    }

}
