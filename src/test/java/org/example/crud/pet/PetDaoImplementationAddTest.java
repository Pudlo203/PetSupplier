package org.example.crud.pet;

import jakarta.transaction.Transactional;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
@Transactional
public class PetDaoImplementationAddTest {

    private static String DB_URL = "jdbc:postgresql://localhost/petDatabase";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pudlom";

    String insertSQL = "INSERT INTO pets (pet_name, kind, owner_id) VALUES (?,?,?)";

    @Test
    public void createNewPet() throws SQLException {
//        String insertSQL = "INSERT INTO pets (pet_name, kind, weight, owner_id) VALUES (?,?,?,?)";
//        String insertSQL = "INSERT INTO pets (pet_name, kind, owner_id) VALUES (?,?,?)";
        String pet_name = "Krystyna";
        String kind = "Cat";
        float weight = 2F;
        int owner_id = 27;
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, pet_name);
        preparedStatement.setString(2, kind);
//        preparedStatement.setFloat(3, weight);
        preparedStatement.setInt(3, owner_id);

//        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//        if (generatedKeys.next()) {
////            preparedStatement.setInt(generatedKeys.getInt(1, pet_id));
//           pet.setPet_id(generatedKeys.getInt(1));
//        } else {
//            throw new SQLException("Creating pet failed");
//        }

        int rowInserted = preparedStatement.executeUpdate();
        assertEquals(1, rowInserted);

        String selectSQL = "SELECT * FROM pets WHERE pet_name=?";
        PreparedStatement statement = conn.prepareStatement(selectSQL);
        statement.setString(1, pet_name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
//            int retrievedId = resultSet.getInt("pet_id");
            String retrievedName = resultSet.getString("pet_name");
            String retrievedKind = resultSet.getString("kind");
//            float retrievedWeight = resultSet.getFloat("weight");
            int retrievedIdOwner = resultSet.getInt("owner_id");

            assertEquals(pet_name, retrievedName);
            assertEquals(kind, retrievedKind);
//            assertEquals(weight, retrievedWeight);
            assertEquals(owner_id, retrievedIdOwner);
        } else{
            throw new SQLException("Failed");
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();
        conn.close();
    }





}