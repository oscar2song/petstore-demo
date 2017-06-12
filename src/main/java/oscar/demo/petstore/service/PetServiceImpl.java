package oscar.demo.petstore.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import oscar.demo.petstore.entity.Pet;
import oscar.demo.petstore.entity.PetCategory;
import oscar.demo.petstore.entity.PetTag;
import oscar.demo.petstore.exception.NotFoundException;
import oscar.demo.petstore.repository.PetRepository;

@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;

	@Autowired
	public PetServiceImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public Pet create(Pet pet) {
		Pet copiedPet = new Pet();

		copiedPet.setTags(new HashSet<>());
		copiedPet.setCategory(new PetCategory(pet.getCategory().getName()));
		copiedPet.setName(pet.getName());
		for (String photoUrl : pet.getPhotoUrls()) {
			copiedPet.addPhotoUrl(photoUrl);
		}
		for (PetTag tag : pet.getTags()) {
			copiedPet.addTag(new PetTag(tag.getName()));
		}
		copiedPet.setStatus(pet.getStatus());

		return petRepository.save(copiedPet);
	}

	@Override
	public Pet get(int petId) {
		return petRepository.findOne(petId);
	}

	@Override
	public Collection<Pet> getAll(String query) {
    return query == null ? petRepository.findAll() : petRepository.findAll(query.toUpperCase());
  }

	@Override
	public Collection<Pet> delete(int petId) {
		try {
			petRepository.delete(petId);
		} catch (EmptyResultDataAccessException ex) {
			throw new NotFoundException(petId);
		}
		return getAll(null);
	}

}
