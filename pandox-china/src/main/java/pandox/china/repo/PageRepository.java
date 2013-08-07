package pandox.china.repo;

import org.springframework.data.repository.CrudRepository;

import pandox.china.model.Page;

import java.util.List;


public interface PageRepository extends CrudRepository<Page, Long> {

	Page getByUrl(String url);

	List<Page> findByUser_Id(Long id);
}
