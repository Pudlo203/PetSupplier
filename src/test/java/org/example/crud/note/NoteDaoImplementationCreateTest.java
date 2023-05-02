package org.example.crud.note;

import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;

public class NoteDaoImplementationCreateTest {
    private static String DB_URL = "jdbc:postgresql://localhost/petDatabase";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pudlom";

    private static final String DELETE_QUERY = "DELETE FROM pets WHERE pet_id=?";

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//        connection.setAutoCommit(false);

    }

    @After
    public void tearDown() throws Exception {
//        connection.rollback();
        connection.close();
    }


}
