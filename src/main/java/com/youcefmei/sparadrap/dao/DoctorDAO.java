package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO implements IDAOObservable<Doctor> {


    @Override
    public Doctor findById(int id) {
        Doctor doctor = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(
                    "SELECT * FROM Doctor d INNER JOIN Users u ON u.id_users = d.id_users WHERE id_doctor = ?",
                    Statement.RETURN_GENERATED_KEYS
            );
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();

            if ( resultSet.next() ) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String mail = resultSet.getString("mail");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                String userId = resultSet.getString("id_users");
                doctor = new Doctor(
                        resultSet.getInt("id_doctor"),
                        firstName,
                        lastName,
                        phone,
                        mail,
                        address,
                        city,
                        areacode,
                        resultSet.getString("registrationnb")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return doctor;
    }

    @Override
    public Doctor create( Doctor doctor) {
        Integer doctorId = null;
        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement("""
                    INSERT INTO Users(`firstname`,`lastname`,`mail`,`address`,`areacode`,`city`,`phone`) 
                    VALUES (?,?,?,?,?,?,?); 
                    """,Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getMail());
            preparedStatement.setString(4, doctor.getAddress());
            preparedStatement.setString(5, doctor.getAreaCode());
            preparedStatement.setString(6, doctor.getCity());
            preparedStatement.setString(7, doctor.getPhone());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer userId = generatedKeys.getInt(1);

                preparedStatement = conn.prepareStatement("INSERT INTO Doctor(`registrationnb`,`Id_Users`)  " +
                        "VALUES (?,?); ",Statement.RETURN_GENERATED_KEYS
                );
                preparedStatement.setString(1, doctor.getRegistrationNb());
                preparedStatement.setInt(2, userId);
                preparedStatement.executeUpdate();

                generatedKeys = preparedStatement.getGeneratedKeys();


                if ( generatedKeys.next()) {
                    conn.commit();
                    conn.setAutoCommit(true);
                    doctorId = generatedKeys.getInt(1);
                    doctor.setDoctorId(doctorId);
                    return doctor;
                }
            }
            conn.rollback();
            preparedStatement.close();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(Doctor doctor) {

        try {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE Doctor SET registrationnb = ?  WHERE id_doctor = ?;",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, doctor.getRegistrationNb());
            preparedStatement.setInt(2,doctor.getDoctorId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer userId = generatedKeys.getInt(1);
                preparedStatement = conn.prepareStatement(
                        """
                        UPDATE Users SET 
                         `firstname` = ?,`lastname` = ? , `mail` = ?, `address` = ? ,`areacode`= ?, `city`= ?,`phone`= ?  
                         WHERE id_users = ?;
                        """,
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
                preparedStatement.setString(1, doctor.getFirstName());
                preparedStatement.setString(2, doctor.getLastName());
                preparedStatement.setString(3, doctor.getMail());
                preparedStatement.setString(4, doctor.getAddress());
                preparedStatement.setString(5, doctor.getAreaCode());
                preparedStatement.setString(6, doctor.getCity());
                preparedStatement.setString(7, doctor.getPhone());
                preparedStatement.setInt(8, userId);
                preparedStatement.executeUpdate();
                generatedKeys = preparedStatement.getGeneratedKeys();
                if ( generatedKeys.next()) {
                    preparedStatement.close();
                    conn.commit();
                    conn.setAutoCommit(true);
                    return true;
                }

            }
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {

            PreparedStatement pStatement = conn.prepareStatement(
            "SELECT u.id_users FROM Doctor d INNER JOIN Users u ON u.id_users = d.id_users WHERE id_doctor = ?;",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                pStatement = conn.prepareStatement("DELETE FROM doctor " +
                                "WHERE Id_Doctor = ? " ,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                pStatement.setInt(1, id);
                pStatement.executeUpdate();
                ResultSet generatedKeys = pStatement.getGeneratedKeys();
                if ( generatedKeys.next() ){

                    pStatement = conn.prepareStatement("DELETE FROM Users " +
                                    "WHERE Id_Users = ? " ,
                            PreparedStatement.RETURN_GENERATED_KEYS);
                    pStatement.setInt(1,userId);
                    pStatement.executeUpdate();
                    generatedKeys = pStatement.getGeneratedKeys();
                    if ( generatedKeys.next() ){
                        
                        return true;
                    }
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM Doctor d INNER JOIN Users u ON d.Id_Users = u.Id_Users",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int idDoctor = resultSet.getInt("id_doctor");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String mail = resultSet.getString("mail");
                String address = resultSet.getString("address");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                int idUsers = resultSet.getInt("id_users");
                String registrationnb = resultSet.getString("registrationnb");

                doctor = new Doctor(idDoctor,firstname,lastname,phone,mail,address,city,areacode,registrationnb);

                doctors.add(doctor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return doctors;
    }



}
