package pandox.china.service.auth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import pandox.china.model.User;
import pandox.china.service.UserService;

@Service
public class AuthService {

	private Logger logger = Logger.getLogger(AuthService.class);

	private final String permission = "ADMIN";

	@Autowired
	private UserService userService;

	public User getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				Object user = authentication.getPrincipal();
				if (user != null && user instanceof User) {
					return (User) user;
				}
			}

		}
		return null;
	}
}
