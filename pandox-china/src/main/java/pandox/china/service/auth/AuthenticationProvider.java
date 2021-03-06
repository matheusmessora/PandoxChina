package pandox.china.service.auth;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pandox.china.model.SocialUser;
import pandox.china.model.User;
import pandox.china.service.UserService;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthenticationProvider implements AuthenticationManager {

	private Logger logger = Logger.getLogger(AuthenticationProvider.class);

	private final String permission = "ROLE_ADMIN";

	private String email;
	private String password;

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {
			if (authentication instanceof UsernamePasswordAuthenticationToken) {

				this.email = authentication.getName();
				this.password = (String) authentication.getCredentials();

				logger.info("usuario=" + email + ", msg=Tentativa de Login");

				User user = userService.findByEmailAndPassword(email, password);
				if (user != null) {
					logger.info("usuario=" + email + ", msg=Usuario autenticado com sucesso");
					return this.createUserAndTokenAuthentication(user);
				}
				throw new BadCredentialsException("Usuario invalido");
			}
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException(e.getLocalizedMessage());
		}
		throw new BadCredentialsException("Erro no Authentication Provider");
	}

	public Authentication createUserAndTokenAuthentication(User user) {
        GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
        user.addRole(simpleGrantedAuthority);
        logger.info("Autenticacao realizada. usuario=" + user);
        return new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
    }

	public Authentication generateSocialUserTokenAuthentication(SocialUser user) {
        GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
        user.addRole(simpleGrantedAuthority);
        logger.info("Autenticacao realizada. SocialUser=" + user);
        return new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
    }
}
