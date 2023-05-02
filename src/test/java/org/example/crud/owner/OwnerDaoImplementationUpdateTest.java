package org.example.crud.owner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OwnerDaoImplementationUpdateTest {
    private static String DB_URL = "jdbc:postgresql://localhost/petDatabase";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "pudlom";

    private static final String UPDATE_OWNER = "update owners set first_name=?, last_name=? where owner_id=?";

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

    //UPDATE OWNER
//        Owner ownerUpdate = ownerDao.readOwner(2);
//        ownerUpdate.setFirst_name("Karolina");
//        ownerUpdate.setLast_name("Ryś");
//        ownerDao.updateOwner(ownerUpdate);
//        System.out.println(ownerUpdate);


    @Test
    public void updateOwner() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OWNER);
        preparedStatement.setString(1, "Janusz");
        preparedStatement.setString(2, "Januszewski");
        preparedStatement.setInt(3, 44);
        int rowsAffected = preparedStatement.executeUpdate();
        assertNotNull(rowsAffected);
        assertEquals(1, rowsAffected);



    }
}
