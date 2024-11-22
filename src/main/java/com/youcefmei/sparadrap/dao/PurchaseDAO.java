package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO implements IDAOObservable<Purchase> {
    private PatientDAO patientDAO = new PatientDAO();
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    private StockDAO stockDAO = new StockDAO();

    @Override
    public Purchase findById(int id) {
        Purchase purchase = null;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM purchase pu LEFT JOIN prescription pr ON pu.Id_Prescription = pr.Id_Prescription WHERE Id_Purchase = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Float price = resultSet.getFloat("price");
                Float priceMutual = resultSet.getFloat("price_mutual");
                LocalDateTime date_buy = resultSet.getTimestamp("date_buy").toLocalDateTime();
                String purchaseRef = resultSet.getString("purchase_ref");
                Integer prescriptionId = resultSet.getInt("id_prescription");
                System.out.println(date_buy);
                if ( prescriptionId != 0 ) {
                    Prescription prescription = prescriptionDAO.findById(prescriptionId);
                    purchase = new Purchase( id, date_buy, prescription );
                    purchase.setTotalAmountWithMutual(priceMutual);
                }
                else {
                    purchase = new Purchase(id,date_buy);
                }
                purchase.setRef(purchaseRef);
                purchase.setTotalAmountWithoutMutual(price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
        return purchase;
    }

    @Override
    public Integer create(Purchase purchase) {
        Integer purchaseId = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement(
                    "INSERT INTO purchase(`price`,`price_mutual`,`date_buy`,`is_paid`) VALUES (?,?,?,?) ",
                    Statement.RETURN_GENERATED_KEYS
            );
            pStatement.setFloat(1, purchase.getTotalAmountWithoutMutual());
            pStatement.setFloat(2, purchase.getTotalAmountWithMutual());
            if ( purchase.getDatetime() == null ){
                pStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            }else{
                pStatement.setTimestamp(3, Timestamp.valueOf(purchase.getDatetime()));
            }
            pStatement.setBoolean(4, purchase.isPaid());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                purchaseId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchaseId;
    }

    @Override
    public boolean update(Purchase purchase) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE purchase SET price = ? , price_mutual = ?, date_buy = ?, is_paid = ? WHERE Id_Purchase = ?",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setFloat(1, purchase.getTotalAmountWithoutMutual());
            preparedStatement.setFloat(2, purchase.getTotalAmountWithMutual());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(purchase.getDatetime()));
            preparedStatement.setBoolean(4, purchase.isPaid());
            preparedStatement.setInt(5, purchase.getPurchaseId());
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
//            PreparedStatement pStatement = conn.prepareStatement(
//                    "DELETE FROM prescription WHERE Id_Purchase = ? " ,
//                    PreparedStatement.RETURN_GENERATED_KEYS
//            );
//            pStatement.setInt(1, id);
//            pStatement.executeUpdate();

            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM purchase " +
                            "WHERE Id_Purchase = ? " ,
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
    public List<Purchase> findAll() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM purchase pu LEFT JOIN prescription pr ON pu.Id_Prescription = pr.Id_Prescription");
            while (resultSet.next()) {
                Purchase purchase = null;
                Integer id = resultSet.getInt("Id_Purchase");
                Float price = resultSet.getFloat("price");
                Float priceMutual = resultSet.getFloat("price_mutual");
                Boolean isPaid = resultSet.getBoolean("is_paid");
                LocalDateTime date_buy = resultSet.getTimestamp("date_buy").toLocalDateTime();
                String purchaseRef = resultSet.getString("purchase_ref");
                Integer prescriptionId = resultSet.getInt("id_prescription");
                if ( prescriptionId != 0) {
                    Prescription prescription = prescriptionDAO.findById( prescriptionId );
                    purchase = new Purchase(id,date_buy, prescription);
                    purchase.setTotalAmountWithMutual(priceMutual);
                }
                else {
                    purchase = new Purchase(id,date_buy);
                }
                purchase.setRef(purchaseRef);
                purchase.setTotalAmountWithoutMutual(price);
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "SELECT * FROM purchase_item WHERE Id_Purchase = ? ",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, id);
                List<PurchaseItem> purchaseItems = new ArrayList<>();
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while (resultSet1.next()) {
                    Stock stock = stockDAO.findById(resultSet1.getInt("stock_id"));
                    purchaseItems.add(
                      new PurchaseItem(
                              id,
                              resultSet1.getInt("qty"),
                              stock.getMedicament(),
                              resultSet1.getFloat("unit_price")
                      )
                    );
                }
                purchase.setPaid(isPaid);
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        } catch (InvalidDateException e) {
            throw new RuntimeException(e);
        }
        return purchases;
    }
}
