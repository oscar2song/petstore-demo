package oscar.demo.petstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import oscar.demo.petstore.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
  @Query("Select c from Pet c where upper(c.name) like %:name%")
  List<Pet> findByNameContaining(String name);
}
