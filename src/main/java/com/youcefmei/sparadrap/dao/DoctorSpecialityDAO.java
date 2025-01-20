package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.DoctorSpeciality;
import com.youcefmei.sparadrap.model.Prescription;
import com.youcefmei.sparadrap.model.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorSpecialityDAO implements IDAOObservable<DoctorSpeciality> {

    @Override
    public DoctorSpeciality findById(int id) {
        DoctorSpeciality doctorSpeciality = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM Speciality WHERE Id_Speciality = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                doctorSpeciality = new DoctorSpeciality(
                        resultSet.getInt("id_speciality"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return doctorSpeciality;
    }

    @Override
    public DoctorSpeciality create(DoctorSpeciality doctorSpeciality) {
        Integer doctorSpecialityId = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(
                    "INSERT INTO Speciality(`name`) VALUES (?,?) ",Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, doctorSpeciality.getName());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                doctorSpecialityId = generatedKeys.getInt(1);
                doctorSpeciality.setId(doctorSpecialityId);
                return doctorSpeciality;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(DoctorSpeciality doctorSpeciality) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE Speciality SET name = ?  WHERE Id_Speciality = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, doctorSpeciality.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                preparedStatement.close();
                return true;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pStatement = conn.prepareStatement(" DELETE FROM Speciality WHERE Id_Speciality = ? " ,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1 , id);
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if ( generatedKeys.next() ){
                return true;
            }
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }

        return false;
    }

    @Override
    public List<DoctorSpeciality> findAll() {
        List<DoctorSpeciality> doctorSpecialities = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Speciality");
            while (resultSet.next()) {

                Integer id = resultSet.getInt("id_speciality");
                String name = resultSet.getString("name");
                DoctorSpeciality doctorSpeciality = new DoctorSpeciality(id,name);
                doctorSpecialities.add(doctorSpeciality);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return doctorSpecialities;
    }
}
