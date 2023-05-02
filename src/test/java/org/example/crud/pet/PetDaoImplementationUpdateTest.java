package org.example.crud.pet;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PetDaoImplementationUpdateTest {

    private static String DB_URL = "jdbc:postgresql://localhost/petDatabase";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pudlom";

    private Connection connection;
    private static final String UPDATE_QUERY = "UPDATE pets SET pet_name = ?, kind = ?, weight = ?, owner_id = ? WHERE pet_id = ?";

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
     public void updatePet() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        statement.setString(1, "Klucha");
        statement.setString(2, "Hippo");
        statement.setFloat(3, 3F);
        statement.setInt(4, 3);
        statement.setInt(5, 8);
        int rowsAffected = statement.executeUpdate();
        assertNotNull(rowsAffected);
        assertEquals(1, rowsAffected);
    }




}