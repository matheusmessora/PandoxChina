package pandox.china.service;

import pandox.china.model.SocialUser;
import pandox.china.model.User;


public interface SocialUserService extends GenericService<SocialUser, Long> {
	
	SocialUser findByUid(Long uid);
}
