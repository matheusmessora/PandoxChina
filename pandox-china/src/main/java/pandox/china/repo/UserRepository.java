package pandox.china.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pandox.china.model.User;


public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByName(String name);


	User getByEmailAndPassword(String email, String password);
}
