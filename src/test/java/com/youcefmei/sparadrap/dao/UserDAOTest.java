package com.youcefmei.sparadrap.dao;

import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserDAOTest {
    private UserDAO userDAO = new UserDAO();
    private static User user;


    @BeforeAll
    public static void setUp() throws InvalidInputException {
        user = new User(null,"firstname","lastname","0101010101","azerty@gmail.com",
                "12 rue blabla","city","89999");
    }

    @Test
    @Order(1)
    public void insertUserValid() throws InvalidInputException {
        assertDoesNotThrow( () ->{
            Integer newId = userDAO.create(user);
            user.setUserId(newId);
        });

    }


    @Test
    @Order(2)
    public void updateUserValid() throws InvalidInputException {
        assertDoesNotThrow( () ->{
            user.setFirstName("firstnameupdated");
            userDAO.update(user);

        });

    }


    @Test
    @Order(3)
    public void deleteUserValid() throws InvalidInputException {
        assertDoesNotThrow( () -> {
            userDAO.delete(user.getUserId());

        });
    }


}
