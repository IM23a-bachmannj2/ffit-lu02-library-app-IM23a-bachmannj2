package ch.bzz.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
    private static final Logger log = LoggerFactory.getLogger(Database.class);

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
            log.error("Failed to load database configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            log.error("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
