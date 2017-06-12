package oscar.demo.petstore;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import oscar.demo.petstore.entity.Pet;
import oscar.demo.petstore.entity.PetCategory;
import oscar.demo.petstore.entity.PetTag;
import oscar.demo.petstore.entity.Pet.Status;
import oscar.demo.petstore.repository.PetRepository;

@SpringBootApplication
public class PetStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);
	}

	/**
	 * This method must be commented when spring.jpa.hibernate.ddl-auto ==
	 * validate in application.properties in order to avoid adding twice these
	 * data in database
	 * 
	 */
	@Bean
	public CommandLineRunner init(PetRepository petRepository) {

		return (args) -> {
			petRepository.save(createPet("Daisy", new PetCategory("Cat"), Arrays.asList("img/cat1.jpg".split(",")),
					Status.AVAILABLE, Arrays.asList(new PetTag("Beautiful cat"))));
			petRepository.save(
					createPet("Doug", new PetCategory("Dog"), Arrays.asList("img/dog4.jpg,img/dog2.jpg".split(",")),
							Status.SOLD, Arrays.asList(new PetTag("Nice dog"), new PetTag("it likes playing"))));
			petRepository.save(createPet("Betty", new PetCategory("Cat"), null, Status.PENDING, null));
			petRepository.save(createPet("Gloria", null,
					Arrays.asList("img/cat3.jpg,img/cat6.jpg,wrongPhotoUrl".split(",")), Status.AVAILABLE, null));

			List<Pet> pets = petRepository.findAll();
			for (Pet pet : pets) {
				System.out.println(pet);
			}
		};
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
