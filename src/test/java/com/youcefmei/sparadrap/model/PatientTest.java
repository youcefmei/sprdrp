package com.youcefmei.sparadrap.model;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient patient;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @BeforeEach
    void setUp() throws InvalidInputException, InvalidDateException {
        patient = new Patient("firstname","lastname","0606060606","blabla@gmail.com","147 rue machin","cityville","45666","1654250312178", LocalDate.now(),null);
    }


    @ParameterizedTest
    @ValueSource(strings = {"10-02-2022","01-01-2000"})
    void setBirthDateValid(String birthDateStr ) {

        assertDoesNotThrow( () -> {
            patient.setBirthDate( LocalDate.parse(birthDateStr, formatter ) );
        });
        assertEquals( LocalDate.parse(birthDateStr, formatter ), patient.getBirthDate());
    }


    @ParameterizedTest
    @ValueSource(strings = {"10-02-2099","01-01-4000"})
    void setBirthDateThrowInvalidDate(String birthDateStr ) {

        assertThrows( InvalidDateException.class  ,() -> {
            patient.setBirthDate( LocalDate.parse(birthDateStr, formatter ) );
        });
    }


    @ParameterizedTest
    @ValueSource(strings = {"1845236410147","2845236410147",})
    void setSecuIdValid(String secId) {
        assertDoesNotThrow(() -> patient.setSecuId(secId));
        assertEquals(secId,patient.getSecuId());
    }


    @ParameterizedTest
    @ValueSource(strings = {"4845236410147","","00","1*","aeaze"})
    @NullAndEmptySource
    void setSecuIdThrowInvalidInput(String secId) {
        Exception ex = assertThrows(InvalidInputException.class, ()  -> patient.setSecuId(secId));
        assertEquals("Le numéro de sécurité social n'est pas valide", ex.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "firstname,Firstname",
            "FiRStNamE,Firstname",
            "FirsTName-comPose blabla,Firstname-compose Blabla"
    })
    void setFirstNameValid(String firstName,String expected) {
        assertDoesNotThrow( ()  -> patient.setFirstName(firstName));
        assertEquals( expected, patient.getFirstName() );
    }


    @ParameterizedTest
    @ValueSource(strings = {"firstname444","FiRStNamE)","FirsTName-compose blabla#",""})
    @NullAndEmptySource
    void setFirstNameInvalidInput(String firstName) {
        assertThrows( InvalidInputException.class ,()  -> patient.setFirstName(firstName));
    }

    @ParameterizedTest
    @CsvSource({
            "Lastname,Lastname",
            "LaStNamE,Lastname",
    })
    void setLastNameValid(String lastName,String expected) {
        assertDoesNotThrow( ()  -> patient.setLastName(lastName));
        assertEquals( expected, patient.getLastName() );
    }

    @ParameterizedTest
    @ValueSource(strings = {"lastname444","LaStNamE)","LasTNameblabla#",""})
    @NullAndEmptySource
    void setLastNameInvalidInput(String lastName) {
        assertThrows( InvalidInputException.class ,()  -> patient.setLastName(lastName));
    }


    @ParameterizedTest
    @CsvSource({
            "0123456789,0123456789",
            "+33644444444,+33644444444",
    })
    void setPhoneValid(String phone,String expected) {
        assertDoesNotThrow( ()  -> patient.setPhone(phone));
        assertEquals( expected, patient.getPhone() );
    }

    @ParameterizedTest
    @ValueSource(strings = {"rftertergdf","0141dgfvxcvv","1111111111111111111111111","01","@"})
    @NullAndEmptySource
    void setPhoneInvalidInput(String phone) {
        assertThrows( InvalidInputException.class ,()  -> patient.setPhone(phone));
    }


    @ParameterizedTest
    @CsvSource({
            "mymail@gmail.com,mymail@gmail.com",
            "AzeazeaezR@yahoo.fR,azeazeaezr@yahoo.fr",
    })
    void setMailValid(String mail,String expected) {
        assertDoesNotThrow( ()  -> patient.setMail(mail));
        assertEquals( expected, patient.getMail() );
    }

    @ParameterizedTest
    @ValueSource(strings = {"mymailgmail.com","mymail@gmail","0141dgfvxcvv","1111111111111111111111111","01","@"})
    @NullAndEmptySource
    void setMailInValidInput(String mail) {
        assertThrows( InvalidInputException.class ,()  -> patient.setMail(mail));
    }

    @ParameterizedTest
    @CsvSource({
            "124 rue du Moulin,124 rue du Moulin",
            "123 impasse des champs,123 impasse des champs",
    })
    void setAddressValid(String address,String expected) {
        assertDoesNotThrow( ()  -> patient.setAddress(address));
        assertEquals( expected, patient.getAddress() );
    }

    @ParameterizedTest
    @ValueSource(strings = {"mymailgmail.com","azzz","0141dgfvxcvv","1111111111111111111111111","01","@"})
    @NullAndEmptySource
    void setAddressInValidInput(String address) {
        assertThrows( InvalidInputException.class ,()  -> patient.setAddress(address));
    }

    @ParameterizedTest
    @CsvSource({
            "Nancy,Nancy",
            "frouard,Frouard",
            "ars-laquenexy,Ars-laquenexy",
    })
    void setCityValid(String city,String expected) {
        assertDoesNotThrow( ()  -> patient.setCity(city));
        assertEquals( expected, patient.getCity() );
    }

    @ParameterizedTest
    @ValueSource(strings = {"mymailgmail.com","0141dgfvxcvv","1111111111111111111111111","01","@"})
    @NullAndEmptySource
    void setCityInValidInput(String city) {
        assertThrows( InvalidInputException.class ,()  -> patient.setCity(city));
    }


    @ParameterizedTest
    @CsvSource({
            "12533,12533",
            "85420,85420",
            "75000,75000",
    })
    void setAreacodeValid(String areaCode,String expected) {
        assertDoesNotThrow( ()  -> patient.setAreaCode(areaCode));
        assertEquals( expected, patient.getAreaCode() );
    }

    @ParameterizedTest
    @ValueSource(strings = {"00000000000000","1","18","7888889","mymailgmail.com","0141dgfvxcvv","1111111111111111111111111","01","@"})
    @NullAndEmptySource
    void setAreacodeInValidInput(String areacode) {
        assertThrows( InvalidInputException.class ,()  -> patient.setAreaCode(areacode));
    }

}