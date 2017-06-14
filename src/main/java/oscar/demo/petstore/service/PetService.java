package oscar.demo.petstore.service;

import java.util.Collection;

import oscar.demo.petstore.entity.Pet;

public interface PetService {
	Pet create(Pet pet);

	Pet get(int petId);

	Collection<Pet> delete(int petId);

  Collection<Pet> getAll();
  
  Collection<Pet> getAll(String query);
}
