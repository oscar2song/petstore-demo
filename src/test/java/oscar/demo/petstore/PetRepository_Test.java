package oscar.demo.petstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import oscar.demo.petstore.entity.Pet;
import oscar.demo.petstore.entity.PetCategory;
import oscar.demo.petstore.entity.PetTag;
import oscar.demo.petstore.entity.Pet.Status;
import oscar.demo.petstore.repository.PetRepository;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PetRepository_Test {

	@Autowired
	private PetRepository petRepository;

	@Before
	public void beforeTest() throws Exception {
		petRepository.deleteAll();
	}

	@Test
	public void checkPetCreation() throws Exception {
		checkCreation(createPet("Daisy", new PetCategory("Fish"), Arrays.asList("img1,img2".split(",")),
				Status.AVAILABLE, Arrays.asList(new PetTag("daisy fish"))));

		checkCreation(createPet("Doug", new PetCategory("Dog"), Arrays.asList("img5".split(",")), Status.SOLD,
				Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));

		checkCreation(createPet("Pepette", new PetCategory("Horse"), Arrays.asList("img5".split(",")), Status.SOLD,
				Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));

		checkCreation(createPet("Loulou", null, Arrays.asList("img5".split(",")), Status.SOLD,
				Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));

		checkCreation(createPet("Betty", null, null, Status.SOLD,
				Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));

		checkCreation(createPet("Louna", null, null, null, Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));

		checkCreation(createPet("Keke", null, null, null, null));

		boolean isPetNameNull = false;
		try {
			createPet(null, new PetCategory("Tortoise"), Arrays.asList("img5".split(",")), Status.SOLD,
					Arrays.asList(new PetTag("a tortoie")));
		} catch (NullPointerException ex) {
			isPetNameNull = true;
		}
		Assert.assertTrue("Error: pet name cannot be null", isPetNameNull);

		System.out.println("Pets created:");
		List<Pet> pets = petRepository.findAll();
		for (Pet pet : pets) {
			System.out.println(pet);
		}
	}

	@Test
	public void checkFetchAllPets() throws Exception {
		List<Pet> expectedPets = new ArrayList<>();
		Pet pet1 = petRepository.save(createPet("Daisy", new PetCategory("Fish"), Arrays.asList("img1,img2".split(",")),
				Status.AVAILABLE, Arrays.asList(new PetTag("daisy fish"))));
		expectedPets.add(pet1);

		Pet pet2 = petRepository.save(createPet("Doug", new PetCategory("Dog"), Arrays.asList("img5".split(",")),
				Status.SOLD, Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));
		expectedPets.add(pet2);

		Pet pet3 = petRepository.save(createPet("Pepette", new PetCategory("Horse"), Arrays.asList("img5".split(",")),
				Status.SOLD, Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));
		expectedPets.add(pet3);

		Pet pet4 = petRepository.save(createPet("Loulou", null, Arrays.asList("img5".split(",")), Status.SOLD,
				Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));
		expectedPets.add(pet4);

		List<Pet> pets = petRepository.findAll();

		assertThat(pets).containsAll(expectedPets);
	}

	@Test
	public void checkPetDeletion() throws Exception {
		List<Pet> expectedPets = new ArrayList<>();
		Pet pet1 = petRepository.save(createPet("Daisy", new PetCategory("Fish"), Arrays.asList("img1,img2".split(",")),
				Status.AVAILABLE, Arrays.asList(new PetTag("daisy fish"))));
		expectedPets.add(pet1);

		Pet pet2 = petRepository.save(createPet("Doug", new PetCategory("Dog"), Arrays.asList("img5".split(",")),
				Status.SOLD, Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));
		expectedPets.add(pet2);

		Pet pet3 = petRepository.save(createPet("Pepette", new PetCategory("Horse"), Arrays.asList("img5".split(",")),
				Status.SOLD, Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));
		expectedPets.add(pet3);

		Pet pet4 = petRepository.save(createPet("Loulou", null, Arrays.asList("img5".split(",")), Status.SOLD,
				Arrays.asList(new PetTag("a dog"), new PetTag("nice dog"))));
		expectedPets.add(pet4);

		assertThat(petRepository.findOne(pet2.getId())).isEqualTo(pet2);
		petRepository.delete(pet2.getId());
		assertThat(petRepository.findOne(pet2.getId())).isEqualTo(null);
	}

	private void checkCreation(Pet pet) {
		petRepository.save(pet);
		Pet expectedPet = petRepository.findOne(pet.getId());

		assertThat(expectedPet).isEqualTo(pet);
	}

	private Pet createPet(String name, PetCategory petCategory, List<String> photoUrls, Status status,
			List<PetTag> tags) throws Exception {
		Pet newPet = new Pet();
		newPet.setName(name);
		newPet.setCategory(petCategory);
		if (photoUrls != null) {
			for (String photoUrl : photoUrls) {
				newPet.addPhotoUrl(photoUrl);
			}
		}
		newPet.setStatus(status);
		if (tags != null) {
			for (PetTag tag : tags) {
				newPet.addTag(tag);
			}
		}
		return newPet;
	}
}
