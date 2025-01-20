package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.MedicamentCategory;
import com.youcefmei.sparadrap.model.State;
import com.youcefmei.sparadrap.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentCategoryDAO implements IDAOObservable<MedicamentCategory> {

    @Override
    public MedicamentCategory findById(int id) {
        MedicamentCategory medicamentCategory = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("""
                    SELECT * FROM MEDICAMENTCATEGORY p WHERE id_medicamentcategory = ? """
            );
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                medicamentCategory = new MedicamentCategory(
                        id,name
                );
            }
            return medicamentCategory;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MedicamentCategory create(MedicamentCategory medcat) {
        Integer medicamentCategoryId = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("""
                            INSERT INTO MEDICAMENTCATEGORY(`name`) VALUES (?) """,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, medcat.getName());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                pStatement.close();
                medicamentCategoryId = generatedKeys.getInt(1);
                medcat.setId(medicamentCategoryId);
                return medcat;
            }
            pStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(MedicamentCategory medcat) {
        try {
            PreparedStatement pStatement = conn.prepareStatement(
                    "UPDATE MEDICAMENTCATEGORY SET `name` = ? WHERE id_medicamentcategory = ? ",
                    Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, medcat.getName());
            pStatement.setInt(2, medcat.getId());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
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
            PreparedStatement pStatement = conn.prepareStatement(
                    "DELETE FROM MEDICAMENTCATEGORY WHERE id_medicamentcategory = ? " ,
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
    public List<MedicamentCategory> findAll() {
        List<MedicamentCategory> medicamentCategories = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medicamentcategory");
            while (resultSet.next()) {

                Integer id = resultSet.getInt("id_state");
                String name = resultSet.getString("name");
                MedicamentCategory medicamentCategory = new MedicamentCategory(id,name);
                medicamentCategories.add(medicamentCategory);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return medicamentCategories;
    }
}
