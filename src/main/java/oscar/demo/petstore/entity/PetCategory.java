package oscar.demo.petstore.entity;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class PetCategory {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int categoryId;
	private String categoryName;

	public PetCategory() {
	}

	public PetCategory(String name) {
		// this.categoryId = id;
		this.categoryName = name;
	}

	public long getId() {
		return categoryId;
	}

	public void setId(int id) {
		this.categoryId = id;
	}

	public String getName() {
		return categoryName;
	}

	public void setName(String name) {
		this.categoryName = name;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PetCategory category = (PetCategory) o;
		return Objects.equals(this.categoryId, category.categoryId)
				&& Objects.equals(this.categoryName, category.categoryName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, categoryName);
	}

	@Override
	public String toString() {
		StringBuilder petCategory = new StringBuilder();

		petCategory.append("{\n");
		petCategory.append("    id: ").append(categoryId).append("\n");
		petCategory.append("    name: ").append(categoryName).append("\n");
		petCategory.append("}");

		return petCategory.toString();
	}
}
