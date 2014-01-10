package pandox.china.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pandox.china.model.Category;
import pandox.china.model.Page;
import pandox.china.model.Phone;
import pandox.china.repo.CategoryRepository;
import pandox.china.repo.PageRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repo;

	private GenericService<Category, Long> getFather() {
		return new GenericServiceImpl(repo);
	}

	@Override
	public Category save(Category entity) {
		return getFather().save(entity);
	}

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        getFather().delete(id);
    }

    @Override
	public ArrayList<Category> findAll() {
		return getFather().findAll();
	}

	@Override
	public Category findOne(Long id) throws IllegalArgumentException {
		return getFather().findOne(id);
	}
}
