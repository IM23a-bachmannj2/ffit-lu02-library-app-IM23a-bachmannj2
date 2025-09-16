package ch.bzz.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            String appConfigPath = "config.properties";

            Properties dbProps = new Properties();
            dbProps.load(new FileInputStream(appConfigPath));

            URL = dbProps.getProperty("URL");
            USER = dbProps.getProperty("USER");
            PASSWORD = dbProps.getProperty("PASSWORD");

        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load database configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
