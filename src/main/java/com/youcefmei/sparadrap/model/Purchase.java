package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Purchase {

    private String id = UUID.randomUUID().toString();
    private LocalDateTime datetime;
    private String datetimeStr;
    private List<Medicament> medicaments = new ArrayList<Medicament>();
    private boolean isPaid;
    private Prescription prescription;
//    private float totalAmount = 0.0f;
    private float totalAmount;


    public Purchase () throws InvalidDateException {
        setDatetime(LocalDateTime.now());

    }

    public Purchase(LocalDateTime datetime) throws  InvalidDateException {
        setDatetime(datetime);
    }

    public Purchase(Prescription prescription) throws  InvalidDateException, InvalidInputException {
        setDatetime(LocalDateTime.now());
        setPrescription(prescription);
    }

    public Purchase(LocalDateTime datetime,Prescription prescription) throws  InvalidDateException, InvalidInputException {
        setDatetime(datetime.plusDays(1));
        setPrescription(prescription);
    }




     public boolean isPaid() {
        return isPaid;
    }

    public LocalDateTime getDatetime() {
        return datetime;
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
        if ( prescription == null ||  (prescription.getPatient().getHealthMutual() == null) ) {
            for (Medicament medicament : medicaments) {
                totalPrice += medicament.getTotalPrice();
            }
        } else{
            HealthMutual healthMutual = prescription.getPatient().getHealthMutual();
            float rate =  healthMutual.getHealthCareRate();
            for (Medicament medicament : medicaments) {
                totalPrice += medicament.getTotalPrice() * ( (100 - rate)/100);
            }
        }
        return totalPrice;
    }

    public String getDatetimeStr() {
        return datetimeStr;
    }

    public void setDatetime(LocalDateTime datetime) throws InvalidDateException {
        if ( (datetime== null)  ) {
            throw new InvalidDateException("La date de facturation ne peut etre null");
        }else if ( datetime.isAfter(LocalDateTime.now() )) {
            throw new InvalidDateException("La date de facturation ne peut etre postérieur à aujourd'hui");
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            System.out.println(datetime);
            this.datetime = datetime;
            datetimeStr = datetime.format(formatter);
        }
    }


    public void addMedicament(Medicament medicament) throws  InvalidInputException {

        if ( medicament == null ) {
            throw  new InvalidInputException("Le mediacament ne peut etre nul");
        } else if (  (prescription == null)  && medicament.isNeedPrescription()   ){
            throw new InvalidInputException("Ce medicament a besoin d'une ordonnance");
        } else {
            // Check if medicament is in the list
            List<Medicament> medicamentFound = medicaments.stream().filter(
                    medicamentTemp -> medicamentTemp.getTitle().equals(medicament.getTitle()) && ( medicamentTemp.getQuantity() == medicament.getQuantity() )
            ).toList();
//            if ( !medicamentFound.isEmpty() ) {
////                throw new InvalidInputException("Déja dans le panier, vous pouvez changer la quantité\n pour modifier la commande");
////
//            } else {
            if ( medicamentFound.isEmpty() ) {
                // Add if not in the list
                if ( prescription != null ) {
                    // With prescription add
                    List<Medicament> medicamentFoundInPrescription = prescription.getMedicaments().stream().filter(
                            medicamentTemp -> medicamentTemp.getTitle().equals(medicament.getTitle())
                    ).toList();

                    if (!medicamentFoundInPrescription.isEmpty()) {
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
        System.out.println("Purchase amount: " + getTotalAmount());
    }


    public void removeMedicament(Medicament medicament){
        if ( medicament != null ) {
            this.medicaments =  medicaments.stream().filter(
                    medicamentTemp -> !medicamentTemp.getTitle().equals(medicament.getTitle())
            ).toList();

        }
    }

    public void setMedicaments(List<Medicament> medicaments) throws  InvalidInputException {
        if ( medicaments == null){
            throw new InvalidInputException("La liste de médicament ne peut etre null");
        } else if ( medicaments.isEmpty() ) {
            throw new InvalidInputException("La liste de médicament ne peut etre vide");
        } else if ( prescription != null ) {

            throw new InvalidInputException("La liste de médicament ne peut etre modifier car il s'agit d'un achat avec ordonnance");
        }else{
            this.medicaments.clear();
            for(Medicament medicament : medicaments){
                System.out.println(medicaments);
                System.out.println(medicament);
                this.addMedicament(medicament);
            }
        }
    }

    public void setPrescription(Prescription prescription) throws  InvalidInputException {
        if (prescription == null){
            throw new InvalidInputException("L'ordonnance ne peut pas etre null");
        } else if (  prescription.getMedicaments().isEmpty() ) {
            throw new InvalidInputException("Cette ordonnance ne contient pas de médicaments");
        } else if ( this.prescription == null && !getMedicaments().isEmpty() ){
            throw new InvalidInputException("Il est impossible d'ajouter une ordonnance si un achat sans ordonnance est en cours");
        } else{
            this.prescription = prescription;
            for (Medicament medicament : prescription.getMedicaments()) {
                addMedicament(medicament);
            }
        }
    }

    public void setPaid(boolean paid) throws InvalidInputException {
        if ( paid && medicaments.isEmpty()) {
            throw new InvalidInputException("La liste de medicament est vide");
        }else{
            isPaid = paid;
        }
    }


    @Override
    public String toString() {
        String title ;

        if (prescription != null) {
            title = "Achat avec ordonnance - " + id + " - " + getDatetimeStr();
        }else{
            title = "Achat sans ordonnance - " + id + " - " + getDatetimeStr();
        }
        return title;
    }
}
