package org.example.crud.pet;

import junit.framework.TestCase;
import org.example.model.Pet;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PetDaoImplementationDeleteTest  {
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


    @Test
    public void deletePetPass() throws SQLException {
        int pet_id = 41;
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, pet_id);
        int rowsAffected = preparedStatement.executeUpdate();
        assertEquals(0, rowsAffected);

    }
    @Test
    public void deletePetFailed() throws SQLException {
        int pet_id = 56;
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
        preparedStatement.setInt(1, pet_id);
        int rowsAffected = preparedStatement.executeUpdate();
        assertNotEquals(0, rowsAffected);

    }




}
