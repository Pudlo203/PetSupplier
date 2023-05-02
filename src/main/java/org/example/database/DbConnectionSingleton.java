package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionSingleton {

    private static DbConnectionSingleton instance;
    private Connection connection;

    private final String url = "jdbc:postgresql://localhost/petDatabase";
    private final String username = "postgres";
    private final String password = "pudlom";

    private DbConnectionSingleton() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
    public static DbConnectionSingleton getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbConnectionSingleton();
            System.out.println("Connected");
        } else if (instance.getConnection().isClosed()) {
            instance = new DbConnectionSingleton();
            System.out.println("Closed connect");
        }
        return instance;
    }

}

