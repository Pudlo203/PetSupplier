package org.example.crud;

import org.example.model.Note;
import org.example.model.NotePet;

import java.util.List;

public interface NoteDao {

    Note createNote(Note note);

    Note readNote(int id);

    void deleteNote(int id);

    List<Note> readAllNotes();

    void updateNote(Note note);

    Note createNoteCreateRelationPet(Note note, NotePet notePet);


}
