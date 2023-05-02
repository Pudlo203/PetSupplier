package org.example.crud;

import org.example.database.DbConnectionSingleton;
import org.example.model.Owner;
import org.example.model.Pet;


import java.sql.*;
import java.util.*;

public class OwnerDaoImplementation implements OwnerDao {
    private static final String CREATE_OWNER_QUERY = "insert into owners (first_name, last_name) values (?,?)";
    private static final String UPDATE_PET_QUERY = "UPDATE pets SET owner_id = ? WHERE pet_id = ?";
    private static final String FIND= "SELECT COUNT(*) FROM pets where owner_id=?";
    private static final String CREATE_PET_QUERY = "INSERT INTO pets (pet_name, kind, weight, owner_id) VALUES (?,?,?,?)";
    private static final String UPDATE_OWNER = "update owners set first_name=?, last_name=? where owner_id=?";
    private static final String FIND_OWNERS_JOIN_LIST_PETS_BY_ID = "select * from owners left join pets on owners.owner_id = pets.owner_id where owners.owner_id=?";
    private static final String FIND_OWNERS_WITHOUT_PETS = "select * from owners left join pets on owners.owner_id = pets.owner_id where pets.owner_id is null;";

    private static final String UPDATE_PETS_ID_NULL="UPDATE pets SET owner_id = NULL WHERE owner_id = ?";

    private static final String DELETE_OWNER = "DELETE FROM owners WHERE owner_id = ?";

    @Override
    public Owner createOwner(Owner owner) {
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(CREATE_OWNER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, owner.getFirst_name());
            preparedStatement.setString(2, owner.getLast_name());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                owner.setOwner_id(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating owner failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owner;
    }

    //    public void createOwnerCreatePet(Owner owner, Pet pet) {
//
//        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
//            conn.setAutoCommit(false);
//            PreparedStatement preparedStatement = conn.prepareStatement(CREATE_OWNER_QUERY, Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setString(1, owner.getFirst_name());
//            preparedStatement.setString(2, owner.getLast_name());
//            preparedStatement.executeUpdate();
//
//
//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                owner.setOwner_id(generatedKeys.getInt(1));
//
//
//                PreparedStatement statement = conn.prepareStatement(CREATE_PET_QUERY, Statement.RETURN_GENERATED_KEYS);
//                statement.setString(1, pet.getPet_name());
//                statement.setString(2, pet.getKind());
//                statement.setFloat(3, pet.getWeight());
//                statement.setInt(4, owner.getOwner_id());
////                int row =
//                statement.executeUpdate();
//                ResultSet generatedKeysPets = statement.getGeneratedKeys();
//                if (generatedKeysPets.next()) {
//                    pet.setPet_id(generatedKeysPets.getInt(1));
//
//                } else {
//                    System.out.println("Błąd podczas dodawania zwierzaka");
//                    conn.rollback();
//                }
//
//            } else {
//                System.out.println("Tutututututu");
//                conn.rollback();
//            }
//            conn.commit();
//            System.out.println("Dodano nowego właściciela i jego zwierzę do bazy danych.");
//        } catch (SQLException e) {
//            System.err.println("Failed to create Owner with Pet");
//        } finally {
//
//        }
//
//    }
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement statement = null;
    ResultSet generatedKeys = null;
    ResultSet generatedKeysPets = null;

    @Override
    public void createOwnerCreatePet(Owner owner, Pet pet) {

        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(CREATE_OWNER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, owner.getFirst_name());
            preparedStatement.setString(2, owner.getLast_name());
            preparedStatement.executeUpdate();


            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                owner.setOwner_id(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating owner failed");
            }
            PreparedStatement statement = conn.prepareStatement(CREATE_PET_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pet.getPet_name());
            statement.setString(2, pet.getKind());
            statement.setFloat(3, pet.getWeight());
            statement.setInt(4, owner.getOwner_id());
            statement.executeUpdate();
            ResultSet generatedKeysPets = statement.getGeneratedKeys();
            if (generatedKeysPets.next()) {
                pet.setPet_id(generatedKeysPets.getInt(1));

            } else {
                System.out.println("Błąd podczas dodawania zwierzaka");  //w 1 catchu się wywala
                conn.rollback();
            }
            conn.commit();
            System.out.println("Dodano nowego właściciela i jego zwierzę do bazy danych.");

        } catch (SQLException e) {
            try {
                System.err.println("Failed to create Owner with Pet");
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Błąd podczas wycofywania transakcji: " + ex.getMessage());
            }
        } finally {
            try {
                if (generatedKeys != null && generatedKeys.isClosed()) generatedKeys.close();
                if (generatedKeysPets != null && generatedKeysPets.isClosed()) generatedKeysPets.close();
                if (preparedStatement != null && preparedStatement.isClosed()) preparedStatement.close();
                if (statement != null && statement.isClosed()) statement.close();
                if (conn != null && conn.isClosed()) conn.close();
            } catch (SQLException e) {
                System.err.println("Błąd podczas zamykania obiektów: " + e.getMessage());
            }

        }

    }
    @Override
    public void createOwnerAndAssignPet(Owner owner, int petId) {
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(CREATE_OWNER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, owner.getFirst_name());
            preparedStatement.setString(2, owner.getLast_name());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                owner.setOwner_id(generatedKeys.getInt(1));

                PreparedStatement statement = conn.prepareStatement(UPDATE_PET_QUERY);
                statement.setInt(2, petId);
                statement.setInt(1, owner.getOwner_id());
                statement.executeUpdate();
            } else {
                System.out.println("Tutututututu");
                conn.rollback();
            }
            conn.commit();


        } catch (SQLException e) {
            System.err.println("Wrong operation");
        }
    }
    @Override
    public void updateOwner(Owner owner) {
        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_OWNER);
            preparedStatement.setString(1, owner.getFirst_name());
            preparedStatement.setString(2, owner.getLast_name());
            preparedStatement.setInt(3, owner.getOwner_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update owner");
        }
    }
    @Override
    public Owner readOwner(int id) {
        Owner ownerJoinListPets = new Owner();
        List<Pet> petJoinOwner = new ArrayList<>();

        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_OWNERS_JOIN_LIST_PETS_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ownerJoinListPets = mapRow(resultSet);

                Pet petResult = mapPetRow(resultSet);
                petJoinOwner.add(petResult);

                ownerJoinListPets.setPetList(petJoinOwner);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Nie udało się znaleźć listy zwierząt przypisanych do właściciela");
        }
        return ownerJoinListPets;
    }


    //ON DELETE CASCADE --->
    /*  @Override
      public void deleteOwner(int id) {
          try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
              PreparedStatement statement = conn.prepareStatement(DELETE_OWNER_QUERY);
              statement.setInt(1, id);
              statement.executeUpdate();
              System.out.println("Deleted owner.");
          } catch (SQLException e) {

              System.err.println("Wrong operation, unable to remove owner who has pets assigned.");
          }
      }*/
     @Override
    public void deleteOwner(int id) {
         try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
             // wyłączenie automatycznego zatwierdzania transakcji
             conn.setAutoCommit(false);

             // sprawdzenie, czy istnieją powiązane rekordy Pet
             PreparedStatement statement = conn.prepareStatement(FIND);
             statement.setInt(1, id);
             ResultSet  rs = statement.executeQuery();
             rs.next();
             int count = rs.getInt(1);
             if (count > 0) {

                 // ustawienie owner_id na null w powiązanych rekordach Pet
                 statement = conn.prepareStatement(UPDATE_PETS_ID_NULL);
                 statement.setInt(1, id);
                 statement.executeUpdate();
             }
             // usunięcie rekordu Ownera
             statement = conn.prepareStatement(DELETE_OWNER);
             statement.setInt(1, id);
             statement.executeUpdate();
             conn.commit();

         }  catch (SQLException e) {
             try {
                 System.err.println("Failed to create Owner with Pet");
                 if (conn != null) {
                     conn.rollback();
                 }
             } catch (SQLException ex) {
                 System.out.println("Błąd podczas wycofywania transakcji: " + ex.getMessage());
             }
         } finally {
             try {
                 if (statement != null && statement.isClosed()) statement.close();
                 if (conn != null && conn.isClosed()) conn.close();
             } catch (SQLException e) {
                 System.err.println("Błąd podczas zamykania obiektów: " + e.getMessage());
             }

         }
     }
    public void readAllOwnersList(List<Owner> ownerList) {
        for (Owner owner : ownerList) {
            System.out.println(owner);
        }
    }

    @Override
    public List<Owner> readOwnerWithoutPets() {
        List<Owner> owners = new ArrayList<>();

        try (Connection conn = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_OWNERS_WITHOUT_PETS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                owners.add(mapRow(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owners;
    }

    private Owner mapRow(ResultSet resultSet) throws SQLException {
        Owner owner = new Owner();
        owner.setOwner_id(resultSet.getInt("owner_id"));
        owner.setFirst_name(resultSet.getString("first_name"));
        owner.setLast_name(resultSet.getString("last_name"));
        return owner;
    }

    private Pet mapPetRow(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        pet.setPet_id(resultSet.getInt("pet_id"));
        pet.setPet_name(resultSet.getString("pet_name"));
        pet.setKind(resultSet.getString("kind"));
        pet.setWeight(resultSet.getFloat("weight"));
        pet.setOwner_id(resultSet.getInt("owner_id"));
        return pet;
    }


}
