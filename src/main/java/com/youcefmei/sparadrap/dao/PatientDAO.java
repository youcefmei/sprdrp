package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.DoctorGeneral;
import com.youcefmei.sparadrap.model.HealthMutual;
import com.youcefmei.sparadrap.model.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements IDAOObservable<Patient> {

    private DoctorGeneralDAO doctorGeneralDAO = new DoctorGeneralDAO();
    private HealthMutualDAO healthMutualDAO = new HealthMutualDAO();


    @Override
    public Patient findById(int id) {
        Patient patient = null;
        DoctorGeneral familyDoctor = null;
        HealthMutual healthMutual = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM patient p INNER JOIN users u ON u.id_users = p.id_users WHERE id_patient = ?"
            );
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String mail = resultSet.getString("mail");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                String areacode = resultSet.getString("areacode");
                String secuid = resultSet.getString("secuid");
                LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
                int familyDoctorId = resultSet.getInt("id_Doctorgeneral");
                int healthMutualId = resultSet.getInt("id_healthmutual");

                if ( familyDoctorId != 0 ){
                    familyDoctor = doctorGeneralDAO.findById(familyDoctorId);
                }

                if ( healthMutualId != 0 ){
                    healthMutual = healthMutualDAO.findById(healthMutualId);
                }

                patient = new Patient(null,firstname,lastname,phone,mail,address,city,areacode,secuid,birthdate,familyDoctor,healthMutual);

            }
            pStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }

    @Override
    public Patient create(Patient patient) {
        Integer patientId = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("INSERT INTO USERS(`firstname`,`lastname`,`mail`,`address`,`areacode`,`city`,`phone`) VALUES (?,?,?,?,?,?,?) ",Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, patient.getFirstName());
            pStatement.setString(2, patient.getLastName());
            pStatement.setString(3, patient.getMail());
            pStatement.setString(4, patient.getAddress());
            pStatement.setString(5, patient.getAreaCode());
            pStatement.setString(6, patient.getCity());
            pStatement.setString(7, patient.getPhone());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                Integer familyDoctorId = null;
                if ( patient.getFamilyDoctor() != null ){
                    familyDoctorId = patient.getFamilyDoctor().getDoctorGeneralId();
                }
                Integer healthMutualId = null;
                if ( patient.getHealthMutual() != null ){
                    healthMutualId = patient.getHealthMutual().getHealthMutualId();
                }
                int userId = generatedKeys.getInt(1);
                pStatement = conn.prepareStatement("INSERT INTO Patient(`secuid`,`birthdate`,`Id_DoctorGeneral`,`Id_HealthMutual`,`Id_Users`) VALUES  (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, patient.getSecuId());
                pStatement.setDate(2,Date.valueOf(patient.getBirthDate()));
                pStatement.setInt(3,familyDoctorId);
                pStatement.setInt(4,healthMutualId);
                pStatement.setInt(5,userId);
                pStatement.executeUpdate();
                generatedKeys = pStatement.getGeneratedKeys();
                if ( generatedKeys.next() ){
                    patientId = generatedKeys.getInt(1);
                    patient.setPatientId(patientId);
                    return patient;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public boolean update(Patient patient) {
        try {
            PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM patient p INNER JOIN users u ON u.id_users = p.id_users WHERE id_patient = ?"
            );
            pStatement.setInt(1,patient.getPatientId());
            ResultSet resultSet = pStatement.executeQuery();
            if ( resultSet.next() ) {
                Integer usersId = resultSet.getInt("id_Users");
                pStatement = conn.prepareStatement("UPDATE USERS SET " +
                                "firstname = ? , lastname = ? , mail = ? , address = ? , areacode = ? , city = ? , phone = ? " +
                                "WHERE id_users = ? " ,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, patient.getFirstName());
                pStatement.setString(2, patient.getLastName());
                pStatement.setString(3, patient.getMail());
                pStatement.setString(4, patient.getAddress());
                pStatement.setString(5, patient.getAreaCode());
                pStatement.setString(6, patient.getCity());
                pStatement.setString(7, patient.getPhone());
                pStatement.setInt(8, usersId);
                pStatement.executeUpdate();
                ResultSet generatedKeys = pStatement.getGeneratedKeys();
//                if (generatedKeys.next()){
                pStatement = conn.prepareStatement(
                        "UPDATE patient SET id_doctorgeneral = ? , id_healthmutual = ? , birthdate = ? WHERE id_patient = ? " ,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                if ( patient.getFamilyDoctor() != null ){
                    pStatement.setInt(1, patient.getFamilyDoctor().getDoctorGeneralId() );
                }
                else{
                   pStatement.setNull(1, Types.INTEGER );
                }
                if ( patient.getHealthMutual() != null ){
                    pStatement.setInt(2, patient.getHealthMutual().getHealthMutualId() );
                }
                else{
                    pStatement.setNull(2, Types.INTEGER );
                }
                pStatement.setDate(3, Date.valueOf(patient.getBirthDate()));
                pStatement.setInt(4,patient.getPatientId());
                pStatement.executeUpdate();
                pStatement.close();
                return true;
//                }
            }
            pStatement.close();
;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT id_users FROM patient WHERE id_patient = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt(1);
                preparedStatement = conn.prepareStatement("DELETE FROM patient WHERE id_patient = ?");
                preparedStatement.setInt(1,id);
                preparedStatement.executeUpdate();

                preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id_users = ?");
                preparedStatement.setInt(1,userId);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        Patient patient  = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM patient p INNER JOIN USERS u ON p.id_users = u.id_users;"
            );
            while (resultSet.next()) {
                HealthMutual healthMutual = null;
                DoctorGeneral doctorGeneral = null;
                int idPatient = resultSet.getInt("id_patient");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String mail = resultSet.getString("mail");
                String address = resultSet.getString("address");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                String secuid = resultSet.getString("secuid");
                LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
                int idDoctorgeneral = resultSet.getInt("id_doctorgeneral");
                int idHealthmutual = resultSet.getInt("id_healthmutual");

                if (idHealthmutual != 0) {
                    healthMutual = healthMutualDAO.findById(idHealthmutual);
                }
                if (idDoctorgeneral != 0) {
                    doctorGeneral = doctorGeneralDAO.findById(idDoctorgeneral);
                }
                patient = new Patient(
                        idPatient,
                        firstName,
                        lastName,
                        phone,mail,address,city,areacode,secuid,birthdate,doctorGeneral,healthMutual
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
        return patients;

    }
}
