package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.Medicament;
import com.youcefmei.sparadrap.model.State;
import com.youcefmei.sparadrap.model.Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StockDAO implements IDAOObservable<Stock>{
    private MedicamentDAO medicamentDAO = new MedicamentDAO();

    @Override
    public Stock findById(int id) {
        Stock stock = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM stock WHERE id_stock = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer qty = resultSet.getInt("qty");
                Integer medicamentId = resultSet.getInt("id_medicament");
                Medicament medicament = medicamentDAO.findById(medicamentId);
                stock = new Stock(id,qty,medicament);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stock;
    }

    @Override
    public Integer create(Stock stock) {
        Integer stockId = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("INSERT INTO stock(`qty`,`Id_Medicament`) VALUES (?,?) ",Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, stock.getQuantity());
            pStatement.setInt(2, stock.getMedicament().getMedicamentId());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                stockId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockId;
    }

    @Override
    public boolean update(Stock stock) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE stock SET qty = ? , Id_Medicament = ? WHERE Id_Stock = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, stock.getQuantity());
            preparedStatement.setInt(2, stock.getMedicament().getMedicamentId());
            preparedStatement.setInt(3, stock.getId());
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
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM stock " +
                            "WHERE id_stock = ? " ,
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
    public List<Stock> findAll() {
        List<Stock> stocks = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM stock");
            while (resultSet.next()) {

                Integer id = resultSet.getInt("id_stock");
                Integer qty = resultSet.getInt("qty");
                Integer medicamentId = resultSet.getInt("Id_medicament");
                Medicament medicament = medicamentDAO.findById(medicamentId);
                Stock stock = new Stock( id,qty,medicament );
                stocks.add(stock);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stocks;
    }
}
