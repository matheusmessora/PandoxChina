package pandox.china.service;

import pandox.china.model.Page;



public interface PageService extends GenericService<Page, Long> {
	
	Page findByUrl(String url);
}
