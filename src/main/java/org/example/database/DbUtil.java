package org.example.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DbUtil
public class DbUtil {

    private static String DB_URL = "jdbc:postgresql://localhost/petDatabase";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pudlom";

    //getConnection()
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
