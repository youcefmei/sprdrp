package com.youcefmei.sparadrap;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    public static void main(String[] args) throws InvalidInputException, DuplicateException, InvalidDateException, PaymentException {
        Pharmacy pharmacy = Pharmacy.getInstance();
        System.out.println(pharmacy.getPatients());
        System.out.println(pharmacy.getDoctorGenerals());
//        Main main = new Main();
//        main.start();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
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