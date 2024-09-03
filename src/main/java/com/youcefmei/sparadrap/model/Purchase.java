package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Purchase {

    private LocalDate date;
    private List<Medicament> medicaments = new ArrayList<Medicament>();
    private boolean isPaid;
    private Prescription prescription;
    private float totalAmount;


    public Purchase () throws InvalidDateException {
        setDate(LocalDate.now());
    }

    public Purchase(LocalDate date) throws Exception {
        setDate(date);
    }

    public Purchase(Prescription prescription) throws Exception {
        setDate(LocalDate.now());
        setPrescription(prescription);
    }

    public Purchase(LocalDate date,Prescription prescription) throws Exception {
        setDate(date);
        setPrescription(prescription);
    }

     public boolean isPaid() {
        return isPaid;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }


    public Prescription getPrescription() {
        return prescription;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setDate(LocalDate date) throws InvalidDateException {
        if ( (date== null)  ) {
            throw new InvalidDateException("La date de facturation ne peut etre null");
        }else if ( date.isAfter(LocalDate.now() )) {
            throw new InvalidDateException("La date de facturation ne peut etre postérieur à aujourd'hui");
        }else{
            this.date = date;
        }
    }

    public void addMedicament(Medicament medicament) throws Exception {
        if ( medicament != null ) {
            // Check if medicament is in the list
            List<Medicament> medicamentFound = medicaments.stream().filter(
                    medicamentTemp -> medicamentTemp.getName().equals(medicament.getName())
            ).toList();
            if ( medicamentFound.size() > 0 ) {
                // Change the quantity if is in the list
                medicaments = medicaments.stream().filter(
                        medicamentTemp -> medicamentTemp.getName().equals(medicamentFound.get(0).getName())
                ).toList();
                medicamentFound.get(0).setQuantity(medicamentFound.get(0).getQuantity() + medicament.getQuantity());
                medicaments.add(medicamentFound.get(0));
            } else {
                // Add if not in the list
                medicaments.add(medicament);
            }
        }
    }


    public void removeMedicament(Medicament medicament) throws Exception {
        if ( medicament != null ) {
            medicaments.remove(medicament);
        }
    }


    public void setMedicaments(List<Medicament> medicaments) throws Exception {
        if ( medicaments == null){
            throw new Exception("La liste de médicament ne peut etre null");
        } else if ( medicaments.isEmpty() ) {
            throw new Exception("La liste de médicament ne peut etre vide");
        } else if ( prescription != null ) {
            throw new Exception("La liste de médicament ne peut etre modifier car il s'agit d'un achat avec ordonnance");
        }else{
            this.medicaments.clear();
            for(Medicament medicament : medicaments){
                this.addMedicament(medicament);
            }
        }
    }


    public void setPrescription(Prescription prescription) throws Exception {
        if (prescription == null){
            throw new Exception("La ordonnance ne peut pas etre null");
        } else if (  prescription.getMedicaments().isEmpty() ) {
            throw new Exception("Cette ordonnance ne contient pas de médicaments");
        } else if ( this.prescription == null && !getMedicaments().isEmpty() ){
            throw new Exception("Il est impossible d'ajouter une ordonnance si un achat sans ordonnance est en cours");
        } else{

            this.setMedicaments(prescription.getMedicaments());
            this.prescription = prescription;
        }
    }

    public void setPaid(boolean paid) throws InvalidInputException {
        if ( paid == true && medicaments.size() > 0 ) {
            throw new InvalidInputException("La liste de medicament est vide");
        }else{
            isPaid = paid;
        }
    }

    @Override
    public String toString() {
        String title ;
        if (prescription != null) {
            title = "Achat avec ordonnance";
        }else{
            title = "Achat sans ordonnance" ;
        }

        return title;
    }
}
