package pandox.china.repo;

import org.springframework.data.repository.CrudRepository;
import pandox.china.model.SocialUser;
import pandox.china.model.User;

import java.util.List;


public interface SocialUserRepository extends CrudRepository<SocialUser, Long> {

//	List<User> findByName(String name);
//	User getByEmailAndPassword(String email, String password);

    SocialUser findByUid(Long uid);
}
