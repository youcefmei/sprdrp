package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.DoctorGeneral;
import com.youcefmei.sparadrap.model.DoctorSpeciality;
import com.youcefmei.sparadrap.model.DoctorSpecialized;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DoctorSpecializedDAO  implements IDAOObservable<DoctorSpecialized>{


    @Override
    public DoctorSpecialized findById(int id) {
        DoctorSpecialized doctorSpecialized = null;
        DoctorSpeciality doctorSpeciality = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(
                    "SELECT * FROM doctorspecialized ds INNER JOIN doctor d ON ds.id_doctor = d.id_doctor INNER JOIN USERS u ON d.id_users = u.id_users INNER JOIN speciality s ON s.id_speciality = ds.id_speciality WHERE id_doctorspecialized = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            pStatement.setInt(1, id);

            ResultSet resultSet = pStatement.executeQuery();

            while (resultSet.next()) {
                doctorSpeciality = new DoctorSpeciality(
                        resultSet.getInt("id_speciality"),
                        resultSet.getString("name")
                );
                doctorSpecialized = new DoctorSpecialized(
                        resultSet.getInt(id),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("phone"),
                        resultSet.getString("mail"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("areacode"),
                        resultSet.getString("registrationnb"),
                        doctorSpeciality
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return doctorSpecialized;
    }

    @Override
    public Integer create(DoctorSpecialized doctorSpecialized) {
        Integer doctorSpecializedId = null;
        try {
            conn.setAutoCommit(false);
            PreparedStatement pStatement = conn.prepareStatement(
                    "INSERT INTO USERS(`firstname`,`lastname`,`mail`,`address`,`areacode`,`city`,`phone`) VALUES (?,?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            pStatement.setString(1, doctorSpecialized.getFirstName());
            pStatement.setString(2, doctorSpecialized.getLastName());
            pStatement.setString(3, doctorSpecialized.getMail());
            pStatement.setString(4, doctorSpecialized.getAddress());
            pStatement.setString(5, doctorSpecialized.getAreaCode());
            pStatement.setString(6, doctorSpecialized.getCity());
            pStatement.setString(7, doctorSpecialized.getPhone());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer userId = generatedKeys.getInt(1);
                pStatement = conn.prepareStatement("INSERT INTO Doctor(registrationnb, Id_Users) VALUES (?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, doctorSpecialized.getRegistrationNb());
                pStatement.setInt(2, userId);
                pStatement.executeUpdate();
                generatedKeys = pStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Integer doctorId = generatedKeys.getInt(1);
                    pStatement = conn.prepareStatement("INSERT INTO doctorspecialized( Id_Speciality,Id_Doctor) VALUES (?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
                    pStatement.setInt(1, doctorSpecialized.getSpeciality().getId());
                    pStatement.setInt(2, doctorId);
                    pStatement.executeUpdate();
                    generatedKeys = pStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        doctorSpecializedId = generatedKeys.getInt(1);
                        conn.commit();
                        conn.setAutoCommit(true);
                        return doctorSpecializedId;
                    }
                }
            }
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctorSpecializedId;
    }

    @Override
    public boolean update(DoctorSpecialized obj) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<DoctorSpecialized> findAll() {
        return List.of();
    }

    public boolean deleteByDoctorId(Integer doctorId) {
        try {
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM doctorspecialized " +
                            "WHERE Id_Doctor = ? " ,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, doctorId);
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if ( generatedKeys.next() ){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public Integer findIdByDoctorId(Integer doctorId) {
        try {
            Integer idSpecialized = null;
            PreparedStatement pStatement = conn.prepareStatement(
                    "SELECT Id_DoctorSpecialized FROM doctor d \n" +
                            " INNER JOIN doctorspecialized ds ON ds.id_doctor = d.id_doctor \n" +
                            "WHERE  d.id_doctor = ? ;",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            pStatement.setInt(1, doctorId);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                idSpecialized =  resultSet.getInt(1);
            }
            return idSpecialized;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
