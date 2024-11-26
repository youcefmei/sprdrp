package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.State;
import com.youcefmei.sparadrap.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IDAOObservable<User> {


    @Override
    public User findById(int id) {
        User user = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM USERS p \n" +
                    " WHERE id_users = ? "
            );
            pStatement.setInt(1, id);
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String mail = resultSet.getString("mail");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                Integer userId = resultSet.getInt("id_users");
                user = new User(
                        userId,firstName,lastName,phone,mail,address,city,areacode
                        );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return user;

    }

    @Override
    public User create(User user) {

        Integer userId = null ;
        try {
            PreparedStatement pStatement = conn.prepareStatement("INSERT INTO USERS(`firstname`,`lastname`,`mail`,`address`,`areacode`,`city`,`phone`) VALUES (?,?,?,?,?,?,?) ",Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, user.getFirstName());
            pStatement.setString(2, user.getLastName());
            pStatement.setString(3, user.getMail());
            pStatement.setString(4, user.getAddress());
            pStatement.setString(5, user.getAreaCode());
            pStatement.setString(6, user.getCity());
            pStatement.setString(7, user.getPhone());
            pStatement.executeUpdate();

            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                userId =  generatedKeys.getInt(1);
                user.setUserId(userId);
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        try {
            PreparedStatement pStatement = conn.prepareStatement("UPDATE USERS " +
                            "SET firstname = ? , lastname = ? , mail = ? , address = ? , areacode = ? , city = ? , phone = ? " +
                            "WHERE id_users = ? " ,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, user.getFirstName());
            pStatement.setString(2, user.getLastName());
            pStatement.setString(3, user.getMail());
            pStatement.setString(4, user.getAddress());
            pStatement.setString(5, user.getAreaCode());
            pStatement.setString(6, user.getCity());
            pStatement.setString(7, user.getPhone());
            pStatement.setInt(8, user.getUserId());
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
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM USERS " +
                            "WHERE id_users = ? " ,
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
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {

                Integer id = resultSet.getInt("id_users");
                String firsname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String mail = resultSet.getString("mail");
                String address = resultSet.getString("address");
                String areacode = resultSet.getString("areacode");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                User user = new User(id,firsname,lastname,mail,address,areacode,city,phone);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
