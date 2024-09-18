package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MedicamentTest {

    private Medicament medicament;

    @BeforeEach
    public void setup() throws InvalidInputException, InvalidDateException {
        medicament = new Medicament("codéine 20 mg","Analgésiques",4.2f,5, LocalDate.now(),true);
    }

   @ParameterizedTest
   @CsvSource({"4.2,5,21","5,1,5.0"})
   public void getTotalPriceValid(float price,int quantity,float priceExpected){
       assertDoesNotThrow(() -> {
                   medicament.setPrice(price);
                   medicament.setQuantity(quantity);
            }
       );
       assertEquals(medicament.getTotalPrice(),priceExpected);
   }

   @ParameterizedTest
   @ValueSource(ints = {-4,-100})
    public void setQuantityInvalid(int quantity){
        assertThrows(InvalidInputException.class, () -> {
            medicament.setQuantity(quantity);
        });
   }

    @ParameterizedTest
    @ValueSource(floats = {-4.0f,-100f,0})
    public void setPriceInvalid(float price){
        assertThrows(InvalidInputException.class, () -> {
            medicament.setPrice(price);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"<","+","$+","&"})
    @EmptySource
    public void setTitleInvalid(String title){
        assertThrows(InvalidInputException.class, () -> {
            medicament.setTitle(title);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"<","+","$+","&","zererez","blabla","22111"})
    @NullAndEmptySource
    public void setCategoryInvalid(String category){
        assertThrows(InvalidInputException.class, () -> {
            medicament.setCategory(category);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Analgésiques","Antibiotiques","Antituberculeux","Antimycosiques",
            "Antiviraux","Antihistaminiques","Antipyrétiques","Antispasmodiques",
            "Cardiologie","Dermatologie","Endocrinologie","Gastro-entérologie","Hématologie",
            "Neurologie","Oncologie","Psychiatrie","Rhumatologie","Urologie"})
    public void setCategoryValid(String category){
        assertDoesNotThrow( () -> {
            medicament.setCategory(category);
        });
    }





}
