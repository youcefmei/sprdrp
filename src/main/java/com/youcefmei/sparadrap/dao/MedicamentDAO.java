package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.Medicament;
import com.youcefmei.sparadrap.model.MedicamentCategory;
import com.youcefmei.sparadrap.model.Stock;
import jakarta.validation.constraints.NotNull;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO implements IDAOObservable<Medicament> {

    @Override
    public Medicament findById(int id) {
        Medicament medicament = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM medicament m INNER JOIN MedicamentCategory mc ON m.id_medicamentcategory = mc.id_medicamentcategory WHERE Id_Medicament = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idMedicamentcaregory = resultSet.getInt("id_medicamentcategory");
                String categoryName = resultSet.getString("mc.name");
                MedicamentCategory medicamentCategory = new MedicamentCategory(idMedicamentcaregory,categoryName);
                medicament = new Medicament(
                        resultSet.getInt("id_medicament"),
                        resultSet.getString("m.name"),
                        medicamentCategory,
                        resultSet.getFloat("price"),
                        resultSet.getDate("date_first").toLocalDate(),
                        resultSet.getBoolean("needprescription")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
        return medicament;
    }

    @Override
    public Medicament create(Medicament medicament) {
        Integer medicamentId = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO medicament(`name`,`date_first`,`price`,`needprescription`,`Id_MedicamentCategory`) VALUES (?,?,?,?,?) ",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, medicament.getTitle());
            preparedStatement.setDate(2, Date.valueOf(medicament.getStartDate()));
            preparedStatement.setFloat(3, medicament.getPrice());
            preparedStatement.setBoolean(4, medicament.isNeedPrescription());
            preparedStatement.setInt(5,medicament.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                medicamentId = generatedKeys.getInt(1);
                medicament.setMedicamentId(medicamentId);
                return medicament;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(Medicament medicament) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE medicament SET name = ? , date_first = ?, price = ?, needprescription = ?, id_medicamentcategory = ? WHERE id_medicament = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, medicament.getTitle());
            preparedStatement.setDate(2, Date.valueOf(medicament.getStartDate()));
            preparedStatement.setFloat(3, medicament.getPrice());
            preparedStatement.setBoolean(4, medicament.isNeedPrescription());
            preparedStatement.setInt(5,medicament.getCategory().getId());
            preparedStatement.setInt(6, medicament.getMedicamentId() );
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
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM medicament WHERE Id_Medicament = ? " ,
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
    public List<Medicament> findAll() {
        List<Medicament> medicaments = new ArrayList<>();
        Medicament medicament = null;
        MedicamentCategory medicamentCategory = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM medicament m INNER JOIN medicamentcategory mc ON m.id_medicamentcategory = mc.id_medicamentcategory ");
            while (resultSet.next()) {

                Integer medicamentId = resultSet.getInt("id_medicament");
                Integer medicamentcategoryId = resultSet.getInt("id_medicamentcategory");
                String medicamentCategoryName = resultSet.getString("mc.name");
                String medicamentName = resultSet.getString("m.name");
                LocalDate dateFirst = resultSet.getDate("date_first").toLocalDate();
                Float price = resultSet.getFloat("price");
                Boolean needprescription = resultSet.getBoolean("needprescription");
                medicamentCategory = new MedicamentCategory(medicamentcategoryId,medicamentCategoryName);
                medicament = new Medicament(
                        medicamentId,
                        medicamentName,
                        medicamentCategory,
                        price,
                        dateFirst,
                        needprescription
                );
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
        return medicaments;
    }

    public Stock findStockByMedicament(@NotNull Medicament medicament) {
        try {
            Stock stock = null;
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM medicament m  INNER JOIN stock s ON s.Id_medicament = m.Id_medicament WHERE m.Id_medicament = ?"
            );
            preparedStatement.setInt(1, medicament.getMedicamentId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stock = new Stock(
                        resultSet.getInt("id_stock"),
                        resultSet.getInt("qty"),
                        medicament
                );
            }
            return stock;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
