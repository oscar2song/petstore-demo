package oscar.demo.petstore.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;

@Entity
@JsonInclude(Include.NON_NULL)
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "categoryId", column = @Column(nullable = true)),
			@AttributeOverride(name = "categoryName", column = @Column(nullable = true)) })
	private PetCategory category;

	@Column(nullable = false)
	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "PHOTO_URL")
	private Set<String> photoUrls = new HashSet<>();

	@OneToMany(mappedBy = "pet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PetTag> tags = new HashSet<>();

	private Status status;

	/**
	 * pet status in the store
	 */
	public enum Status {
		AVAILABLE("available"),

		PENDING("pending"),

		SOLD("sold");

		private String value;

		Status(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}
	}

	public Pet() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PetCategory getCategory() {
		return category;
	}

	public void setCategory(PetCategory category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public Set<String> getPhotoUrls() {
		return this.photoUrls;
	}

	public void setPhotoUrls(Set<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public Pet addPhotoUrl(String photoUrl) {
		this.photoUrls.add(photoUrl);
		return this;
	}

	public Set<PetTag> getTags() {
		return this.tags;
	}

	public Set<PetTag> setTags(Set<PetTag> tags) {
		return this.tags = tags;
	}

	public Pet addTag(PetTag tag) {
		this.tags.add(tag);
		if (tag.getPet() != this) {
			tag.setPet(this);
		}
		return this;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || (getClass() != o.getClass())) {
			return false;
		}
		Pet pet = (Pet) o;
		return Objects.equals(this.id, pet.id) && Objects.equals(this.category, pet.category)
				&& Objects.equals(this.name, pet.name) && Objects.equals(this.photoUrls, pet.photoUrls)
				&& Objects.equals(this.tags, pet.tags) && Objects.equals(this.status, pet.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, category, name, photoUrls, tags, status);
	}

	@Override
	public String toString() {
		StringBuilder pet = new StringBuilder();

		pet.append("class Pet {\n");
		pet.append("    id: ").append(id).append("\n");
		pet.append("    category: ").append(category).append("\n");
		pet.append("    name: ").append(name).append("\n");
		pet.append("    photoUrls: ").append(photoUrls).append("\n");
		pet.append("    tags: ").append(tags).append("\n");
		pet.append("    status: ").append(status).append("\n");
		pet.append("}");

		return pet.toString();
	}
}
