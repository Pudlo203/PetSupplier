package org.example.crud;

import org.example.database.DbConnectionSingleton;
import org.example.model.Note;
import org.example.model.NotePet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImplementation implements NoteDao {

    private static final String CREATE_NOTE = "INSERT INTO notes (description) VALUES (?)";
    private static final String CREATE_RELATION_NOTES_PETS = "insert into notes_pets (pet_id, note_id) values (?,?)";

    private static final String FIND_NOTE_BY_ID = "SELECT * FROM notes WHERE note_id =? ";
    private static final String DELETE_NOTE = "DELETE FROM notes WHERE note_id=?";
    private static final String FIND_ALL_NOTES = "SELECT * FROM notes order by note_id asc";

    private static final String UPDATE_NOTE = "UPDATE notes SET description =? WHERE note_id=?";

    private static final String COUNT_NOTES = "SELECT COUNT(*) FROM notes_pets WHERE note_id=?";
    private static final String DELETE_NOTES_FROM_NOTES_PETS = "DELETE from notes_pets where note_id=?";

    @Override
    public Note createNote(Note note) {
        try (Connection connection = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NOTE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getDescription());
            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                note.setNote_id(generatedKey.getInt(1));
            } else {
                throw new SQLException("Creating note failed");
            }

        } catch (SQLException e) {
            System.err.println("Fail : " + e.getMessage());
        }
        return note;
    }

    @Override
    public Note createNoteCreateRelationPet(Note note, NotePet notePet) {
        try (Connection connection = DbConnectionSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NOTE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getDescription());
            preparedStatement.executeUpdate();
            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                note.setNote_id(generatedKey.getInt(1));
            } else {
                throw new SQLException("Creating note failed");
            }
            preparedStatement = connection.prepareStatement(CREATE_RELATION_NOTES_PETS, Statement.RETURN_GENERATED_KEYS);
            //new object Notes_pets
            preparedStatement.setInt(1, notePet.getPet_id());
            preparedStatement.setInt(2, note.getNote_id());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                notePet.setId(rs.getInt(1));
            } else {
                throw new SQLException("Creating note failed");
            }
            System.out.println("Success");
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Fail : " + e.getMessage());
        }
        return note;
    }

    @Override
    public Note readNote(int id) {
        Note note = new Note();
        try (Connection connection = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_NOTE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                note = mapRow(resultSet);
            } else {
                System.err.println("Note not found");
                return null;
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Failed reading " + e.getMessage());
        }
        return note;
    }

    @Override
    public void deleteNote(int id) {
        try (Connection connection = DbConnectionSingleton.getInstance().getConnection()) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement(COUNT_NOTES);
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    //usuwanie powiązania
                    preparedStatement = connection.prepareStatement(DELETE_NOTES_FROM_NOTES_PETS);
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();
                    //usuwanie rekordu
                    preparedStatement = connection.prepareStatement(DELETE_NOTE);
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();
                    System.out.println("Deleted");
                } else {
                    System.err.println("We have not that note");
                }
                connection.commit();
            } catch (SQLException e) {
                System.err.println("Failed: " + e.getMessage());
                connection.rollback();
            }

        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
        }

    }

    @Override
    public List<Note> readAllNotes() {
        List<Note> noteList = new ArrayList<>();

        try (Connection connection = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_NOTES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                noteList.add(mapRow(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Failed: " + e.getMessage());
        }

        return noteList;
    }


    @Override
    public void updateNote(Note note) {
        try (Connection connection = DbConnectionSingleton.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NOTE);
            preparedStatement.setString(1, note.getDescription());
            preparedStatement.setInt(2, note.getNote_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Note mapRow(ResultSet resultSet) throws SQLException {
        Note note = new Note();
        note.setNote_id(resultSet.getInt("note_id"));
        note.setDescription(resultSet.getString("description"));
        return note;
    }

    public void readAllNotesList(List<Note> noteList) {
        for (Note note : noteList) {
            System.out.println(note);
        }
    }


}
