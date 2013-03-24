package pandox.china.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pandox.china.model.User;
import pandox.china.repo.UserRepository;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends GenericServiceImpl<User, Long, UserRepository> implements UserService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public User findByEmailAndPassword(String email, String password) {
		if(StringUtils.isBlank(email) || StringUtils.isBlank(password)){
			throw new IllegalArgumentException("E-mail ou senha obrigatórios.");
		}
		
		return repo.getByEmailAndPassword(email.trim().toLowerCase(), password.trim().toLowerCase());
	}
}
