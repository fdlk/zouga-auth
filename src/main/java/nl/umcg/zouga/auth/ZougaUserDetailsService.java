package nl.umcg.zouga.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ZougaUserDetailsService implements UserDetailsService {

	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		// TODO: extend JdbcDaoImpl en kijk in de Zouga database
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		String fullName = "Onbekend";
		if ("6914".equals(username)) {
			fullName = "Fleur Kelpin";
		}
		return new ZougaUser(username, fullName, grantedAuths);
	}

}
