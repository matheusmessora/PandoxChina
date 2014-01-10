package pandox.china.repo;

import org.springframework.data.repository.CrudRepository;
import pandox.china.model.Category;
import pandox.china.model.Page;

import java.util.List;


public interface CategoryRepository extends CrudRepository<Category, Long> {

}
