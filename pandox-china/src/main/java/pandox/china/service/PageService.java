package pandox.china.service;

import pandox.china.model.Page;

import java.util.List;


public interface PageService extends GenericService<Page, Long> {
	
	Page findByUrl(String url);

    List<Page> findByUser_Id(Long id);
}
