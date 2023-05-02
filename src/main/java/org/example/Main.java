package org.example;

import org.example.crud.*;
import org.example.database.DbConnectionSingleton;
import org.example.model.Note;
import org.example.model.NotePet;
import org.example.model.Owner;
import org.example.model.Pet;


import java.sql.CallableStatement;
import java.sql.SQLData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

//        DbUtil.connect();
//        DbConnectionSingleton.getInstance();

        PetDaoImplementation petDao = new PetDaoImplementation();
        OwnerDaoImplementation ownerDao = new OwnerDaoImplementation();
        NoteDaoImplementation noteDao = new NoteDaoImplementation();

        //INSERT INTO
//        Pet pet = new Pet();
//        pet.setPet_name("Szczekuś");
//        pet.setKind("Dog");
//        pet.setWeight(20.3F);
//        pet.setOwner_id(2);
//        System.out.println(petDao.create(pet));

        //SELECT * FROM PETS WHERE PET_ID=?
//        DbConnectionSingleton.getInstance();
//        Pet pet = petDao.read(36);
//        System.out.println(pet);
//        DbConnectionSingleton.getInstance();

        //DELETE PET
//        petDao.delete(13);

        //UPDATE PET
//        Pet petUpdate = petDao.read(8);
//        petUpdate.setPet_name("Updatuś");
//        petUpdate.setKind("Hamster");
//        petUpdate.setWeight(0.7F);
//        petUpdate.setOwner_id(4);
//        petDao.update(petUpdate);
//        System.out.println(petUpdate);


        //SELECT * FROM PETS
//        DbConnectionSingleton.getInstance();
//        Pet[] pets = petDao.findAll();
//        petDao.readAllPetsArray(pets);
//        DbConnectionSingleton.getInstance();

        //SELECT * FROM PETS WHERE OWNER=? LIST
//        List<Pet> pet = petDao.readFindById(27);
//        petDao.readAllPetsList(pet);

        //SELECT * FROM PETS WHERE OWNER_ID IS NULL
//       List<Pet> pets =  petDao.findWithNullOwnerId();
//       petDao.readAllPetsList(pets);




        ////////////////////////////////////////////////////

        //DELETE FROM OWNERS WHERE OWNER_ID=?
//        ownerDao.deleteOwner(45);

        //INSERT INTO OWNER
//        Owner owner = new Owner();
//        owner.setFirst_name("Cezary");
//        owner.setLast_name("Cezarski");
//        ownerDao.createOwner(owner);
//        System.out.println(owner);


        //CREATE NEW OWNER AND UPDATE PET -> NEW ID NEW OWNER OLD PET
//        Owner createOwner = new Owner();
//        createOwner.setFirst_name("Karolina");
//        createOwner.setLast_name("Racz");
//        ownerDao.createOwnerAndAssignPet(createOwner,6 );


        //UPDATE OWNER
//        Owner ownerUpdate = ownerDao.readOwner(2);
//        ownerUpdate.setFirst_name("Karolina");
//        ownerUpdate.setLast_name("Ryś");
//        ownerDao.updateOwner(ownerUpdate);
//        System.out.println(ownerUpdate);


        //CREATE OWNER CREATE PET
//        Owner createOwner = new Owner();
//        createOwner.setFirst_name("Anna");
//        createOwner.setLast_name("Kot");
//
//        Pet createPet = new Pet();
//        createPet.setPet_name("Szczekuś");
//        createPet.setKind("Dog");
//        createPet.setWeight(4F);
//        ownerDao.createOwnerCreatePet(createOwner, createPet);


        //SELECT OWNERS WITH PETS
//        System.out.println(ownerDao.readOwner(31));

        //SELECT OWNER WHERE PET IS NULL
//        List<Owner> owner = ownerDao.readOwnerWithoutPets();
//        ownerDao.readAllOwnersList(owner);


        ////////////////////////////////////////////////////
        //NOTES PETS

        //INSERT INTO NOTE
//        Note note = new Note();
//        note.setDescription("nowy");
//        noteDao.createNote(note);


        //SELECT * FROM NOTE WHERE ID=?
//        Note note = noteDao.readNote(4);
//        System.out.println(note);

        //DELETE FROM NOTES WHERE NOTES=?
//        noteDao.deleteNote(4);

        //FIND ALL NOTES
//        List<Note> notes = noteDao.readAllNotes();
//        noteDao.readAllNotesList(notes);

        //UPDATE NOTE
//        Note noteUpdate = noteDao.readNote(9);
//        noteUpdate.setDescription("uwaga bardzo gryzie");
//        noteDao.updateNote(noteUpdate);
//        System.out.println(noteUpdate);

        // INSERT CREATE NOTE CREATE RELATION NOTE_PET
//        Note note = new Note();
//        NotePet notePet = new NotePet();
//        note.setDescription("RELACJA");
//        notePet.setPet_id(5);
//        noteDao.createNoteCreateRelationPet(note, notePet);


        ////////////////////////////


    }
}