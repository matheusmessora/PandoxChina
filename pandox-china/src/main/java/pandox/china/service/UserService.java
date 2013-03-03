package pandox.china.service;

import org.springframework.stereotype.Service;

import pandox.china.model.User;
import pandox.china.repo.UserRepository;


@Service
public class UserService extends GenericServiceImpl<User, Long, UserRepository> {
}
