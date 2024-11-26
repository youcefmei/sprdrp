package com.youcefmei.sparadrap;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.youcefmei.sparadrap.dao.*;
import com.youcefmei.sparadrap.dataseed.PharmacySeeder;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Doctor;
import com.youcefmei.sparadrap.model.DoctorGeneral;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class Main extends Application {
    public static void main(String[] args) throws InvalidInputException, DuplicateException, InvalidDateException, PaymentException, IOException, SQLException, ClassNotFoundException {

        config();
        launch();
    }


    private static void config() throws IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();
        InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream("application.properties");
        props.load(resourceAsStream);
        resourceAsStream.close();

    }


    @Override
    public void start(Stage stage) throws Exception {

        URL url = Main.class.getResource("/views/dashboard.fxml");
        if (url == null) {
            System.err.println("Resource not found!");
        } else {
            System.out.println("Resource found: " + url);
        }

        FXMLLoader loader = new FXMLLoader( url );

        Parent root = loader.load();

        Scene scene = new Scene(root);
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("/views/asset/icon.png")));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/views/asset/icon.png")));

        stage.setTitle("Sparadrap");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }




}