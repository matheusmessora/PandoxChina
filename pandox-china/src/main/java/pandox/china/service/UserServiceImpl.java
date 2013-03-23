package pandox.china.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pandox.china.model.User;
import pandox.china.repo.UserRepository;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends GenericServiceImpl<User, Long, UserRepository> implements UserService {
}
