package oscar.demo.petstore;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author WaleedK
 */
public enum Permission implements GrantedAuthority {
	CREATE,
	READ,
	UPDATE,
	DELETE;

	@Override
	public String getAuthority() {
		return name();
	}

	public static final Permission[] USER_PERMISSIONS = new Permission[]{
			READ,
	};

	public static final Permission[] ADMIN_PERMISSIONS = new Permission[]{
			CREATE,
			READ,
			UPDATE,
			DELETE
	};
}
