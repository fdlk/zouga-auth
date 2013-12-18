package nl.umcg.zouga.auth;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ZougaAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private final String authenticationUrl;
	private UserDetailsService userDetailsService;

	public ZougaAuthenticationProvider(String authenticationUrl){
		this.authenticationUrl = authenticationUrl;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	private RestTemplate template = new RestTemplate();

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken token)
			throws AuthenticationException {
		String name = token.getName();
		String password = token.getCredentials().toString();
		try {
			String response = template.getForObject(authenticationUrl, String.class, name, password);
			//TODO: strenger checken op juiste formaat AUTHENTICATED
			if(response == null || !response.contains("AUTHENTICATED")){
				throw new BadCredentialsException(response);
			}
		} catch(RestClientException ex){
			throw new AuthenticationServiceException("Error trying to authenticate using Zouga.", ex);
		}
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		return userDetailsService.loadUserByUsername(username);
	}

	

}
