package com.youcefmei.sparadrap;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.youcefmei.sparadrap.dao.DoctorDAO;
import com.youcefmei.sparadrap.dao.PatientDAO;
import com.youcefmei.sparadrap.dao.StateDAO;
import com.youcefmei.sparadrap.dao.UserDAO;
import com.youcefmei.sparadrap.dataseed.PharmacySeeder;
import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;
import com.youcefmei.sparadrap.manage.Pharmacy;
import com.youcefmei.sparadrap.model.Doctor;
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
        boolean isInitFakeData = props.getProperty("app.isInitFakeData").equals("true");

        // populate pharmacy with data

//        String BDD = props.getProperty("app.mysql.bdd");
//        String url = props.getProperty("app.mysql.url");
//        String user = props.getProperty("app.mysql.user");
//        String password = props.getProperty("app.mysql.password");
//        System.out.println(url);
//
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setURL(url + BDD);
//        Connection conn = dataSource.getConnection(user,password);
//        Statement stmtDs = conn.createStatement();
//        ResultSet resultSet = stmtDs.executeQuery("SELECT * FROM sprdrp.users ");
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("firstname"));
//        }
//
//        conn.close();

//        boolean isInitFakeData = true;
        if (isInitFakeData) {
            PharmacySeeder pharmacySeeder = new PharmacySeeder();
        }
    }


    @Override
    public void start(Stage stage) throws Exception {

//        URL url = getClass().getResource("/views/dashboard.fxml");
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