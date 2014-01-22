package pandox.china.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pandox.china.dto.CategoryDTO;
import pandox.china.model.Category;
import pandox.china.repo.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repo;

	private GenericService<CategoryDTO, Long> getFather() {
		return new GenericServiceImpl(repo);
	}

	@Override
	public CategoryDTO save(CategoryDTO entity) {
//		return getFather().save(entity);
        return null;
	}

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        getFather().delete(id);
    }

    @Override
    public List<CategoryDTO> findAll() {
        Iterable<Category> categories = repo.findAll();


        List<CategoryDTO> resultList = new ArrayList<CategoryDTO>();
        for (Category category : categories) {
            resultList.add(new CategoryDTO(category));
        }

        return resultList;
    }


    @Override
	public CategoryDTO findOne(Long id) throws IllegalArgumentException {
        Category one = repo.findOne(id);
        return new CategoryDTO(one);
    }
}
