package pandox.china.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pandox.china.model.SocialUser;
import pandox.china.repo.SocialUserRepository;
import pandox.china.repo.UserRepository;

import java.util.ArrayList;


@Service
@Transactional(readOnly = true)
public class SocialUserServiceImpl implements SocialUserService {

	@Autowired
	private SocialUserRepository repo;
	
	private GenericService<SocialUser, Long> getFather() {
		return new GenericServiceImpl(repo);
	}

//	@Override
//	public User findByEmailAndPassword(String email, String password) {
//		if(StringUtils.isBlank(email) || StringUtils.isBlank(password)){
//			throw new IllegalArgumentException("E-mail ou senha obrigatórios.");
//		}
//
//		return repo.getByEmailAndPassword(email.trim().toLowerCase(), password.trim().toLowerCase());
//	}

	@Override
	public SocialUser save(SocialUser entity) {
		return getFather().save(entity);
	}

    @Override
	public ArrayList<SocialUser> findAll() {
		return getFather().findAll();
	}

	@Override
	public SocialUser findOne(Long id) throws IllegalArgumentException {
		return getFather().findOne(id);
	}

    @Override
    public SocialUser findByUid(Long uid) {
        return repo.findByUid(uid);
    }
}
