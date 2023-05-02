package org.example.crud.owner;

import junit.framework.TestCase;
import org.example.model.Owner;
import org.example.model.Pet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OwnerDaoImplementationCreateTest {

    //testy transakcji


    //testy podczas usuwania ownera
    private static String DB_URL = "jdbc:postgresql://localhost/petDatabase";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pudlom";

    private Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO pets (pet_name, kind, weight, owner_id) VALUES (?,?,?,?)";
    private static final String CREATE_OWNER_QUERY = "insert into owners (first_name, last_name) values (?,?)";


    @Before
    public void setUp() throws Exception {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        connection.setAutoCommit(false);
    }

    @After
    public void tearDown() throws Exception {
        connection.rollback();
        connection.close();
    }


    @Test
    public void createOwnerCreatePetTest() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_OWNER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, "Jan");
            preparedStatement.setString(2, "Klucha");
//            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                int owner_id = generatedKey.getInt(1);
                assertNotNull(generatedKey);
//                assertEquals(43, generatedKey.getInt(1));

                PreparedStatement preparedStatement1 = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);

                preparedStatement1.setString(1, "Kudłacz");
                preparedStatement1.setString(2, "Dog");
                preparedStatement1.setFloat(3, 3F);
                preparedStatement1.setInt(4, owner_id);
//                preparedStatement1.executeUpdate();
                ResultSet generatedKeysPets = preparedStatement1.getGeneratedKeys();

                if (generatedKeysPets.next()) {
                  int pet_id = generatedKeysPets.getInt(1);
                    assertNotNull(generatedKey);
//                    assertEquals(56, pet_id);
                } else {
                    System.out.println("Tatatata");
                    connection.rollback();
                }
            }
            connection.commit();

        } catch (SQLException e) {
            System.err.println("Błąd" + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Błąd podczas wycofywania transakcji: " + ex.getMessage());
            }
        }
    }
}