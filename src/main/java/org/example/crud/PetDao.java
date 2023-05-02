package org.example.crud;

import org.example.model.Owner;
import org.example.model.Pet;

import java.util.List;

public interface PetDao {

    Pet create(Pet pet);

    Pet read(int id);

    void delete(int id);

    void update(Pet pet);

    Pet[] findAll();

    public List<Pet> readFindById(int ownerId);

    List<Pet> findWithNullOwnerId();


}
