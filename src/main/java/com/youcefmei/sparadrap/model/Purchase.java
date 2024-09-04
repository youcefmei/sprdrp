package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Purchase {

    private String id = UUID.randomUUID().toString();
    private LocalDate date;
    private List<Medicament> medicaments = new ArrayList<Medicament>();
    private boolean isPaid;
    private Prescription prescription;
    private float totalAmount = 0.0f;



    public Purchase () throws InvalidDateException {
        setDate(LocalDate.now());
    }

    public Purchase(LocalDate date) throws NullPointerException, InvalidDateException {
        setDate(date);
    }

    public Purchase(Prescription prescription) throws NullPointerException, InvalidDateException, InvalidInputException {
        setDate(LocalDate.now());
        setPrescription(prescription);
    }

    public Purchase(LocalDate date,Prescription prescription) throws NullPointerException, InvalidDateException, InvalidInputException {
        setDate(date);
        setPrescription(prescription);
    }

     public boolean isPaid() {
        return isPaid;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }


    public Prescription getPrescription() {
        return prescription;
    }

    public float getTotalAmount(){
        float totalPrice = 0;
        if ( prescription == null ) {
            for (Medicament medicament : medicaments) {
                totalPrice += medicament.getPrice();
            }
        } else{
            HealthMutual healthMutual = prescription.getPatient().getHealthMutual();
            float rate =  (healthMutual!=null) ? healthMutual.getHealthCareRate():0;
            for (Medicament medicament : medicaments) {
                totalPrice += medicament.getPrice() * ( 1 - rate);
            }
        }
        return totalPrice;
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


    public void addMedicament(Medicament medicament) throws NullPointerException, InvalidInputException {

        if ( medicament == null ) {
            throw  new NullPointerException("Le mediacament ne peut etre nulle");
        } else if (  (prescription == null)  && medicament.isNeedPrescription()   ){
            throw new InvalidInputException("Ce medicament a besoin d'une ordonnance");
        } else {
            // Check if medicament is in the list
            List<Medicament> medicamentFound = medicaments.stream().filter(
                    medicamentTemp -> medicamentTemp.getName().equals(medicament.getName())
            ).toList();
            if ( medicamentFound.size() > 0 ) {
                // Change the quantity if is in the list
                medicamentFound.getFirst().setQuantity(medicamentFound.getFirst().getQuantity() + medicament.getQuantity());
                medicaments.add(medicamentFound.getFirst());
            } else {
                // Add if not in the list
                if ( prescription != null ) {
                    // With prescription add
                    List<Medicament> medicamentFoundInPrescription = prescription.getMedicaments().stream().filter(
                            medicamentTemp -> medicamentTemp.getName().equals(medicament.getName())
                    ).toList();
                    if ( medicamentFoundInPrescription.size() > 0 ) {
                        medicaments.add(medicament);
                    } else {
                        throw new InvalidInputException("Ce medicament n'est pas dans l'ordonnance");
                    }
                } else{
                    // Without prescription add
                   medicaments.add(medicament);
                }

            }
        }
    }


    public void removeMedicament(Medicament medicament) throws Exception {
        if ( medicament != null ) {
            medicaments.remove(medicament);
        }
    }


    public void setMedicaments(List<Medicament> medicaments) throws NullPointerException, InvalidInputException {
        if ( medicaments == null){
            throw new NullPointerException("La liste de médicament ne peut etre null");
        } else if ( medicaments.isEmpty() ) {
            throw new NullPointerException("La liste de médicament ne peut etre vide");
        } else if ( prescription != null ) {

            throw new NullPointerException("La liste de médicament ne peut etre modifier car il s'agit d'un achat avec ordonnance");
        }else{
            this.medicaments.clear();
            for(Medicament medicament : medicaments){
                System.out.println(medicaments);
                System.out.println(medicament);
                this.addMedicament(medicament);
            }
        }
    }


    public void setPrescription(Prescription prescription) throws NullPointerException, InvalidInputException {
        if (prescription == null){
            throw new NullPointerException("L'ordonnance ne peut pas etre null");
        } else if (  prescription.getMedicaments().isEmpty() ) {
            throw new NullPointerException("Cette ordonnance ne contient pas de médicaments");
        } else if ( this.prescription == null && !getMedicaments().isEmpty() ){
            throw new NullPointerException("Il est impossible d'ajouter une ordonnance si un achat sans ordonnance est en cours");
        } else{
            this.prescription = prescription;
            for (Medicament medicament : prescription.getMedicaments()) {
                addMedicament(medicament);
            }
        }
    }

    public void setPaid(boolean paid) throws InvalidInputException {
        if ( paid == true && medicaments.size() == 0 ) {
            throw new InvalidInputException("La liste de medicament est vide");
        }else{
            isPaid = paid;
        }
    }


    @Override
    public String toString() {
        String title ;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateStr = date.format(formatter);
        if (prescription != null) {
            title = "Achat avec ordonnance - " + id + " - " + dateStr;
        }else{
            title = "Achat sans ordonnance - " + id + " - " + dateStr;
        }
        return title;
    }
}
