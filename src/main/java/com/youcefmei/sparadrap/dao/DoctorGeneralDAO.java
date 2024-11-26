package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.DoctorGeneral;

import javax.print.Doc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorGeneralDAO implements IDAOObservable<DoctorGeneral> {


    @Override
    public DoctorGeneral findById(int id) {
        DoctorGeneral doctorGeneral = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM Doctorgeneral dg \n" +
                    "INNER JOIN doctor d ON d.id_doctor = dg.id_doctor " +
                    "INNER JOIN users u ON u.id_users = d.id_users " +
                    "WHERE id_doctorgeneral = ?"
            );
            pStatement.setInt(1, id);

            ResultSet resultSet = pStatement.executeQuery();

            while (resultSet.next()) {
                doctorGeneral = new DoctorGeneral(
                        id,
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("phone"),
                        resultSet.getString("mail"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("areacode"),
                        resultSet.getString("registrationnb")
                        );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return doctorGeneral;

    }

    @Override
    public DoctorGeneral create(DoctorGeneral doctorGeneral) {
        Integer doctorGeneralId = null;
        try {
            conn.setAutoCommit(false);
            PreparedStatement pStatement = conn.prepareStatement(
                    "INSERT INTO USERS(`firstname`,`lastname`,`mail`,`address`,`areacode`,`city`,`phone`) VALUES (?,?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            pStatement.setString(1, doctorGeneral.getFirstName());
            pStatement.setString(2, doctorGeneral.getLastName());
            pStatement.setString(3, doctorGeneral.getMail());
            pStatement.setString(4, doctorGeneral.getAddress());
            pStatement.setString(5, doctorGeneral.getAreaCode());
            pStatement.setString(6, doctorGeneral.getCity());
            pStatement.setString(7, doctorGeneral.getPhone());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Integer userId = generatedKeys.getInt(1);
                pStatement = conn.prepareStatement("INSERT INTO Doctor(registrationnb, Id_Users) VALUES (?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, doctorGeneral.getRegistrationNb());
                pStatement.setInt(2, userId);
                pStatement.executeUpdate();
                generatedKeys = pStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Integer doctorId = generatedKeys.getInt(1);
                    doctorGeneral.setDoctorId(doctorId);

                    pStatement = conn.prepareStatement("INSERT INTO DoctorGeneral( Id_Doctor) VALUES (?)",PreparedStatement.RETURN_GENERATED_KEYS);
                    pStatement.setInt(1, doctorId);
                    pStatement.executeUpdate();
                    generatedKeys = pStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        doctorGeneralId = generatedKeys.getInt(1);
                        doctorGeneral.setDoctorGeneralId(doctorGeneralId);
                        conn.commit();
                        conn.setAutoCommit(true);
                        return doctorGeneral;
                    }
                }
            }
        conn.rollback();
        conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(DoctorGeneral doctorGeneral) {

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT dg.id_doctor,d.id_users FROM doctorgeneral dg INNER JOIN doctor d ON d.id_doctor = dg.id_doctor INNER JOIN USERS u ON d.id_users = u.id_users  WHERE id_doctorgeneral = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setInt(1,doctorGeneral.getDoctorGeneralId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idUsers = resultSet.getInt("id_users");
                int idDoctor = resultSet.getInt("id_doctor");
                preparedStatement = conn.prepareStatement(
                        "UPDATE doctor SET registrationnb = ? WHERE id_doctor = ?",
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
                preparedStatement.setString(1, doctorGeneral.getRegistrationNb());
                preparedStatement.setInt(2, idDoctor);
                preparedStatement.executeUpdate();

                preparedStatement = conn.prepareStatement(
                        "UPDATE users SET firstname = ? , lastname = ? , mail = ? , address = ? , areacode = ? , city = ? , phone = ? WHERE id_users = ?",
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
                preparedStatement.setString(1, doctorGeneral.getFirstName());
                preparedStatement.setString(2, doctorGeneral.getLastName());
                preparedStatement.setString(3, doctorGeneral.getMail());
                preparedStatement.setString(4, doctorGeneral.getAddress());
                preparedStatement.setString(5, doctorGeneral.getAreaCode());
                preparedStatement.setString(6, doctorGeneral.getCity());
                preparedStatement.setString(7, doctorGeneral.getPhone());
                preparedStatement.setInt(8, idUsers);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM doctorgeneral dg INNER JOIN doctor d ON d.id_doctor = dg.id_doctor INNER JOIN USERS u ON d.id_users = u.id_users WHERE id_doctorgeneral = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idUsers = resultSet.getInt("id_users");
                int idDoctor = resultSet.getInt("id_doctor");
                preparedStatement = conn.prepareStatement(
                        "DELETE FROM DoctorGeneral WHERE id_doctorgeneral = ? ",
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {

                    preparedStatement = conn.prepareStatement(
                            "DELETE FROM Doctor WHERE id_doctor = ? ",
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );

                    preparedStatement.setInt(1, idDoctor);
                    preparedStatement.executeUpdate();
                    generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {

                        preparedStatement = conn.prepareStatement(
                                "DELETE FROM USERS WHERE id_users = ? ",
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );
                        preparedStatement.setInt(1, idUsers);
                        preparedStatement.executeUpdate();
                        generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            conn.commit();
                            conn.setAutoCommit(true);
                            return true;
                        }
                    }
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
    public List<DoctorGeneral> findAll() {
        List<DoctorGeneral> doctorGenerals = new ArrayList<>();
        DoctorGeneral doctorGeneral = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM doctorgeneral dg INNER JOIN doctor d ON d.id_doctor = dg.id_doctor INNER JOIN USERS u ON d.id_users = u.id_users;"
            );
            while (resultSet.next()) {
                int idDoctorgeneral = resultSet.getInt("id_doctorgeneral");
                int idDoctor = resultSet.getInt("id_doctor");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String mail = resultSet.getString("mail");
                String address = resultSet.getString("address");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                String registrationnb = resultSet.getString("registrationnb");
                doctorGeneral = new DoctorGeneral(
                    idDoctorgeneral,
                    firstName,
                    lastName,
                        phone,mail,address,city,areacode,registrationnb
                );
                doctorGeneral.setDoctorId(idDoctor);
                doctorGenerals.add(doctorGeneral);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return doctorGenerals;
    }

    public boolean deleteByDoctorId(Integer doctorId) {
        try {
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM doctorgeneral " +
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



//    public Integer findIdByDoctorId(Integer doctorId) {
//        try {
//            Integer idDoctorGeneral = null;
//            PreparedStatement pStatement = conn.prepareStatement(
//                    "SELECT Id_DoctorGeneral FROM doctor d \n" +
//                            " INNER JOIN doctorgeneral dg ON dg.id_doctor = d.id_doctor \n" +
//                            "WHERE  d.id_doctor = ? ;",
//                    PreparedStatement.RETURN_GENERATED_KEYS
//            );
//            pStatement.setInt(1, doctorId);
//            ResultSet resultSet = pStatement.executeQuery();
//            if (resultSet.next()) {
//                idDoctorGeneral =  resultSet.getInt(1);
//            }
//            return idDoctorGeneral;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
}
