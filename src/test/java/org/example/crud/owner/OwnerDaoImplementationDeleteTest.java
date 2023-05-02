package org.example.crud.owner;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class OwnerDaoImplementationDeleteTest {
    private static String DB_URL = "jdbc:postgresql://localhost/petDatabase";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pudlom";

    private static final String DELETE_OWNER_QUERY = "DELETE FROM owners WHERE owner_id=?";

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

    @Test
    public void deleteOwner() throws SQLException {
        int owner_id = 9;
        PreparedStatement statement = connection.prepareStatement(DELETE_OWNER_QUERY);
        statement.setInt(1, owner_id);
        int rowAffected = statement.executeUpdate();
        assertEquals(1, rowAffected);



    }

    @Test
    public void dontDeleteOwnerForeignKeyWithPet() throws SQLException {
        int owner_id = 4;
        PreparedStatement statement = connection.prepareStatement(DELETE_OWNER_QUERY);
        statement.setInt(1, owner_id);
        int rowAffected = statement.executeUpdate();
        assertEquals(1, rowAffected);
    }


}
