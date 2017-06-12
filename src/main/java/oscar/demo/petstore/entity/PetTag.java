package oscar.demo.petstore.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PET_TAG")
public class PetTag {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pet_tag_id")
	private Pet pet;

	public PetTag() {
	}

	public PetTag(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
		if (!pet.getTags().contains(this)) {
			pet.addTag(this);
		}
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PetTag tag = (PetTag) o;
		return Objects.equals(this.id, tag.id) && Objects.equals(this.name, tag.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		StringBuilder petTag = new StringBuilder();

		petTag.append("{\n");
		petTag.append("    id: ").append(id).append("\n");
		petTag.append("    name: ").append(name).append("\n");
		petTag.append("}");

		return petTag.toString();
	}
}
