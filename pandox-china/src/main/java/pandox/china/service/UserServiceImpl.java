package pandox.china.service;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pandox.china.model.User;
import pandox.china.repo.UserRepository;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	
	private GenericService<User, Long> getFather() {
		return new GenericServiceImpl(repo);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		if(StringUtils.isBlank(email) || StringUtils.isBlank(password)){
			throw new IllegalArgumentException("E-mail ou senha obrigatórios.");
		}
		
		return repo.getByEmailAndPassword(email.trim().toLowerCase(), password.trim().toLowerCase());
	}

	@Override
	public User save(User entity) {
		return getFather().save(entity);
	}

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        throw new RuntimeException("Method not implemented");
    }

    @Override
	public ArrayList<User> findAll() {
		return getFather().findAll();
	}

	@Override
	public User findOne(Long id) throws IllegalArgumentException {
		return getFather().findOne(id);
	}

}
