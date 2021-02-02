package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/minions_db";

    public static Connection setConnection(String user, String password) {

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection =  null;

        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, properties);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
