package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.HealthMutual;
import com.youcefmei.sparadrap.model.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StateDAO implements IDAOObservable<State> {

    @Override
    public State findById(int id) {
        State state = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM state WHERE id_state = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String code = resultSet.getString("code");
                state = new State(id,name,code);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return state;
    }

    @Override
    public Integer create(State state ) {
        Integer stateId = null;
        try {
            PreparedStatement pStatement = conn.prepareStatement("INSERT INTO STATE(`name`,`code`) VALUES (?,?) ",Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, state.getName());
            pStatement.setString(2, state.getCode());
            pStatement.executeUpdate();
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                stateId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stateId;
    }

    @Override
    public boolean update(State state) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE state SET name = ? , code = ? WHERE id_state = ?", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, state.getName());
            preparedStatement.setString(2, state.getCode());
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
            PreparedStatement pStatement = conn.prepareStatement("DELETE FROM STATE " +
                            "WHERE id_state = ? " ,
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
    public List<State> findAll() {
        List<State> states = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM state");
            while (resultSet.next()) {

                Integer id = resultSet.getInt("id_state");
                String name = resultSet.getString("name");
                String code = resultSet.getString("code");
                State state = new State(id,name,code);
                states.add(state);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
        return states;
    }
}
