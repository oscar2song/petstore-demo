package oscar.demo.petstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	/**
	 * Generated version UID
	 */
	private static final long serialVersionUID = -4273520762862253694L;

	public NotFoundException(int petId) {
		super("Could not find pet with id: '" + String.valueOf(petId) + "'");
	}
}