package pandox.china.repo;

import org.springframework.data.repository.CrudRepository;

import pandox.china.model.Page;


public interface PageRepository extends CrudRepository<Page, Long> {

	Page getByUrl(String url);
}
