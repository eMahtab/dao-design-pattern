package net.mahtabalam.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final Properties properties;

    static {
        InputStream inputStream = DatabaseConnectionManager.class.getClassLoader()
                                   .getResourceAsStream(CONFIG_FILE_NAME);
        properties = new Properties();
        try {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
        properties.setProperty("user", properties.getProperty("db.user"));
        properties.setProperty("password", properties.getProperty("db.password"));
    }

    public Connection getConnection() {
        final String DB_URL = properties.getProperty("db.url");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, properties);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }
}
