package nl.umcg.zouga.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ZougaUser extends User{

	private static final long serialVersionUID = 1L;

	private final String fullName;
	
	public ZougaUser(String username, String fullName,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, "", authorities);
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}

}
