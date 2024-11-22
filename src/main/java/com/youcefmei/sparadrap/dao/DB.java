package com.youcefmei.sparadrap.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.youcefmei.sparadrap.Main;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection conn;
    private static DB db;

    private DB() {

    }

    public static DB getInstance()  {
        if (db == null) {
            try {
                db = new DB();
                Properties props = new Properties();
                InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream("application.properties");
                props.load(resourceAsStream);
                    resourceAsStream.close();

                String BDD = props.getProperty("app.mysql.bdd");
                String url = props.getProperty("app.mysql.url");
                String user = props.getProperty("app.mysql.user");
                String password = props.getProperty("app.mysql.password");

                Class.forName("com.mysql.cj.jdbc.Driver");
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setURL(url + BDD);
                conn = dataSource.getConnection(user, password);

            } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return db;

    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection()  {
        try {
            conn.close();
            db = null ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

