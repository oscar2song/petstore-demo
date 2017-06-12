package oscar.demo.petstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import oscar.demo.petstore.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

  @Query("FROM Pet a WHERE upper(a.name) like :query% ")
  List<Pet> findAll(@Param("query") String query);
}
