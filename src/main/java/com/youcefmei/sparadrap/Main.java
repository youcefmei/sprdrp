package com.youcefmei.sparadrap;

import com.youcefmei.sparadrap.dataseed.PharmacySeeder;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main extends Application {
    public static void main(String[] args) throws InvalidInputException, DuplicateException, InvalidDateException, PaymentException, IOException {

        config();

        launch();
    }

    private static void config() throws IOException {
//        Properties props = new Properties();
//        InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream("application.properties");
//        props.load(resourceAsStream);
//        resourceAsStream.close();
//        boolean isInitFakeData = props.getProperty("app.isInitFakeData").equals("true");

        // populate pharmacy with data


        boolean isInitFakeData = true;
        if (isInitFakeData) {
            PharmacySeeder pharmacySeeder = new PharmacySeeder();
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/views/asset/icon.png")));

        stage.setTitle("Sparadrap");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }




}