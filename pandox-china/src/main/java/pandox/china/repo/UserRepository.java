package pandox.china.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pandox.china.model.User;


public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByName(String name);

}
