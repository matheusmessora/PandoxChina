package pandox.china.service;

import pandox.china.model.User;


public interface UserService extends GenericService<User, Long> {
	
	User findByEmailAndPassword(String email, String password);
}
