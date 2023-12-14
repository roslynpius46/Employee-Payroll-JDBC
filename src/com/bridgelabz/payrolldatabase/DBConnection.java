package com.bridgelabz.payrolldatabase;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @desc Class that represents connection MySQL database
 */
public class DBConnection {

    private static Connection connection;

    /**
     * @desc To get connection to the database
     * @return connection object
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                loadProperties();
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to the database!");
            }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return connection;
    }

    /**
     * @desc Close the connection to database
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                System.out.println("Closing DB connection...");
                connection.close();
                System.out.println("DB connection closed!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    private static String url;
    private static String username;
    private static String password;

    /**
     * @desc load properties like url, user, password of the database from config.properties
     */
    private static void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("No properties defined");
                return;
            }
            properties.load(input);
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.user");
            password = properties.getProperty("jdbc.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
