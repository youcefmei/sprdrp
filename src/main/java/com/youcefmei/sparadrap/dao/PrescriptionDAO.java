package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO implements IDAOObservable<Prescription>{
    PatientDAO patientDAO = new PatientDAO();
    DoctorDAO doctorDAO = new DoctorDAO();
//    PurchaseDAO purchaseDAO = new PurchaseDAO();
    MedicamentDAO medicamentDAO = new MedicamentDAO();

    @Override
    public Prescription findById(int id) {
        Prescription prescription = null;
        Patient patient = null;
        Doctor doctor = null;
//        Purchase purchase = null;
        List<PrescriptionLine> prescriptionLines = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM prescription WHERE id_prescription = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Integer patientId = resultSet.getInt("id_patient");
                Integer doctorId = resultSet.getInt("id_doctor");
                Integer purchaseId = resultSet.getInt("id_purchase");
                LocalDate date = resultSet.getDate("date_prescription").toLocalDate();
                patient = patientDAO.findById(patientId);
//                if (purchase != null) {
//                    purchase = purchaseDAO.findById(purchaseId);
//                }
                doctor = doctorDAO.findById(doctorId);
                prescription = new Prescription(id,date,patient,doctor,prescriptionLines );
            }
            preparedStatement = conn.prepareStatement("SELECT * FROM prescription_line WHERE id_prescription = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idMedicament = resultSet.getInt("id_medicament");
                if (idMedicament != 0) {
                    Medicament medicament = medicamentDAO.findById(idMedicament);
                    PrescriptionLine prescriptionLine = new PrescriptionLine(
                            resultSet.getInt("id_prescription"),
                            resultSet.getInt("qty"),
                           medicament
                    );
                    prescriptionLines.add(prescriptionLine);
                }
            }
            if (prescription != null) {
                prescription.setPrescriptionLines(prescriptionLines);
            }
        }
        catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }  catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
        return prescription;

    }

    @Override
    public Integer create(Prescription prescription) {
        Integer prescriptionId = null;
        try {
            conn.setAutoCommit(false);



            PreparedStatement pStatement = conn.prepareStatement(
                    "INSERT INTO prescription(`date_prescription`,`id_patient`,`Id_Doctor`) VALUES (?,?,?) ",
                    Statement.RETURN_GENERATED_KEYS
            );
            pStatement.setDate(1, Date.valueOf(prescription.getDate()));
            pStatement.setInt(2, prescription.getPatient().getPatientId() );
            pStatement.setInt(3, prescription.getDoctor().getDoctorId() );
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                prescriptionId = generatedKeys.getInt(1);

                for ( PrescriptionLine prescriptionLine : prescription.getPrescriptionLines()){
                    pStatement = conn.prepareStatement(
                            "INSERT INTO prescription_line(`id_prescription`,`Id_Medicament`,`qty`) VALUES (?,?,?) ",
                            Statement.RETURN_GENERATED_KEYS
                    );
                    pStatement.setInt(1,prescriptionId);
                    pStatement.setInt(2,prescriptionLine.getMedicament().getMedicamentId());
                    pStatement.setInt(3,prescriptionLine.getQuantity());
                    pStatement.executeUpdate();

                }
                conn.commit();
                conn.setAutoCommit(true);
            }
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prescriptionId;
    }

    @Override
    public boolean update(Prescription prescription) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Prescription> findAll() {
        return List.of();
    }
}
