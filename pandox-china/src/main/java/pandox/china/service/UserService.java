package pandox.china.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pandox.china.model.User;
import pandox.china.repo.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository dao;

	public User save(User user) {
      user.setPassword("aaa");
//      user = dao.save(user);
		return user;
	}
	
	public List<User> findAll(){
		return (List<User>) dao.findAll();
	}
}
