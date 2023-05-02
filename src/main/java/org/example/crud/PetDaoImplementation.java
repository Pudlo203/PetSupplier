package org.example.crud;

import org.example.database.DbConnectionSingleton;
import org.example.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetDaoImplementation implements PetDao {
    private static final String CREATE_QUERY = "INSERT INTO pets (pet_name, kind, weight, owner_id) VALUES (?,?,?,?)";
    private static final String FIND_QUERY = "SELECT * FROM pets WHERE pet_id=?";
    private static final String DELETE_QUERY = "DELETE FROM pets WHERE pet_id=?";
    private static final String UPDATE_QUERY = "UPDATE pets SET pet_name = ?, kind = ?, weight = ?, owner_id = ? WHERE pet_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM pets order by pet_id asc";
    private static final String FIND_QUERY_WHERE_OWNER_ID = "select * from pets where owner_id=?";

    private static final String FIND_PETS_WITH_NULL_ID_OWNER ="SELECT * FROM pets WHERE owner_id IS NULL";
    @Override
    public Pet create(Pet pet) {
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pet.getPet_name());
            preparedStatement.setString(2, pet.getKind());
            preparedStatement.setFloat(3, pet.getWeight());
            preparedStatement.setInt(4, pet.getOwner_id());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                pet.setPet_id(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating pet failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }
    @Override
    public Pet read(int id) {
        Pet pet = new Pet();
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_QUERY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               pet =  mapRow(resultSet);
            } else {
                System.err.println("Pet not found");
                return null;
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }
    @Override
    public void delete(int id) {
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Pet pet) {
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, pet.getPet_name());
            statement.setString(2, pet.getKind());
            statement.setFloat(3, pet.getWeight());
            statement.setInt(4, pet.getOwner_id());
            statement.setInt(5, pet.getPet_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void readAllPetsArray(Pet[] pets) {
        for (Pet pet : pets) {
            System.out.println(pet);
        }
    }
     public void readAllPetsList(List<Pet> petList){
        for(Pet pet : petList){
            System.out.println(pet);
        }
     }

    private static Pet[] addToArray(Pet p, Pet[] pets) {
        Pet[] tmpPet = Arrays.copyOf(pets, pets.length + 1); //kopia o 1+
        tmpPet[pets.length] = p; //do ostatniej
        return tmpPet; //nowa tab
    }

    @Override
    public Pet[] findAll() {
        Pet[] petArr = new Pet[0];
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Pet pet= mapRow(resultSet);
                petArr = addToArray(pet, petArr);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return petArr;
    }

    @Override
    public List<Pet> readFindById(int ownerId) {
        List<Pet> resultList = new ArrayList<>();

        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_QUERY_WHERE_OWNER_ID);
            preparedStatement.setInt(1, ownerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
               resultList.add(mapRow(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return resultList;
    }

    @Override
    public List<Pet> findWithNullOwnerId() {
        List<Pet> pets = new ArrayList<>();

        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_PETS_WITH_NULL_ID_OWNER);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                pets.add(mapRow(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return pets;
    }

    public Pet mapRow(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        pet.setPet_id(resultSet.getInt("pet_id"));
        pet.setPet_name(resultSet.getString("pet_name"));
        pet.setKind(resultSet.getString("kind"));
        pet.setWeight(resultSet.getFloat("weight"));
        pet.setOwner_id(resultSet.getInt("owner_id"));
        return pet;
    }



}
