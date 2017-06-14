package oscar.demo.petstore.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import oscar.demo.petstore.entity.Pet;
import oscar.demo.petstore.service.PetService;

@RestController
@RequestMapping("/petstore")
public class PetController {

  private final PetService petService;

  @Autowired
  PetController(PetService petService) {
    this.petService = petService;
  }

  @RequestMapping(value = "/pets", method = RequestMethod.GET)
  public Collection<Pet> getPets() {
    return petService.getAll();
  }

  @RequestMapping(value = "/pet/{petId}", method = RequestMethod.GET)
  public Pet findPet(@PathVariable int petId) {
    return petService.get(petId);
  }

  @RequestMapping(value = "/pet", method = RequestMethod.POST)
  public ResponseEntity<?> createPet(@RequestBody Pet pet) {
    Pet newPet = petService.create(pet);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPet.getId()).toUri());

    return new ResponseEntity<>(newPet, httpHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/pet/{petId}", method = RequestMethod.DELETE)
  public Collection<Pet> deletePet(@PathVariable int petId) {
    return petService.delete(petId);
  }

}
