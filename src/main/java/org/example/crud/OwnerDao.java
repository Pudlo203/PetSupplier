package org.example.crud;

import org.example.model.Owner;
import org.example.model.Pet;

import java.util.List;

public interface OwnerDao {

    Owner createOwner(Owner owner);

    void createOwnerCreatePet(Owner owner, Pet pet);
    void createOwnerAndAssignPet(Owner owner, int petId);
    void updateOwner (Owner owner);

    Owner readOwner(int id);

    void deleteOwner(int id);

    List<Owner> readOwnerWithoutPets();






}
