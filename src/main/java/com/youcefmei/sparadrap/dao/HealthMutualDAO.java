package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.HealthMutual;
import com.youcefmei.sparadrap.model.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthMutualDAO implements IDAOObservable<HealthMutual> {

    private StateDAO stateDAO = new StateDAO() ;

    @Override
    public HealthMutual findById(int id) {
        HealthMutual healthMutual = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM HealthMutual WHERE Id_HealthMutual = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String mail = resultSet.getString("mail");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                Float healthCareRate = resultSet.getFloat("rate");
                State state = stateDAO.findById(resultSet.getInt("id_state"));

                healthMutual = new HealthMutual(id,name,phone,mail,address,areacode,city,state,healthCareRate);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return healthMutual;
    }

    @Override
    public HealthMutual create(HealthMutual healthMutual) {
        Integer healthMutualId = null;
        try {

            PreparedStatement pStatement = conn.prepareStatement("""
                    INSERT INTO HealthMutual(`name`,`address`,`areacode`,`city`,`phone`,`mail`,`rate`,`Id_state`) 
                    VALUES (?,?,?,?,?,?,?,?) """,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, healthMutual.getName());
            pStatement.setString(2, healthMutual.getAddress());
            pStatement.setString(3, healthMutual.getAreaCode());
            pStatement.setString(4, healthMutual.getCity());
            pStatement.setString(5, healthMutual.getPhone());
            pStatement.setString(6, healthMutual.getMail());
            pStatement.setDouble(7,healthMutual.getHealthCareRate());
            pStatement.setInt(8, healthMutual.getState().getId());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if ( generatedKeys.next() ){
                healthMutualId = generatedKeys.getInt(1);
                healthMutual.setHealthMutualId(healthMutualId);
                return healthMutual;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(HealthMutual healthMutual) {
        try {
            PreparedStatement pStatement = conn.prepareStatement("""
                      UPDATE HealthMutual SET 
                      name = ? , address = ? , areacode = ? , city = ? , phone = ? , mail = ? , rate = ? , id_state = ? 
                      WHERE Id_HealthMutual = ? """ ,PreparedStatement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, healthMutual.getName());
            pStatement.setString(2, healthMutual.getAddress());
            pStatement.setString(3, healthMutual.getAreaCode());
            pStatement.setString(4, healthMutual.getCity());
            pStatement.setString(5, healthMutual.getPhone());
            pStatement.setString(6, healthMutual.getMail());
            pStatement.setFloat(7, healthMutual.getHealthCareRate());
            pStatement.setInt(8, healthMutual.getState().getId());
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

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pStatement = conn.prepareStatement("DELETE HealthMutual WHERE Id_HealthMutual = ? " ,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, id);
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

    @Override
    public List<HealthMutual> findAll() {
        List<HealthMutual> healthMutuals = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HealthMutual");
            while (resultSet.next()) {
                int idHealthMutual = resultSet.getInt("Id_HealthMutual");
                String name = resultSet.getString("Name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String mail = resultSet.getString("mail");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                Float healthCareRate = resultSet.getFloat("rate");
                State state = stateDAO.findById(resultSet.getInt("id_state"));

                HealthMutual healthMutual = new HealthMutual(idHealthMutual ,name,phone,mail,address,areacode,city,state,healthCareRate);
                healthMutuals.add(healthMutual);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return healthMutuals;
    }
}
