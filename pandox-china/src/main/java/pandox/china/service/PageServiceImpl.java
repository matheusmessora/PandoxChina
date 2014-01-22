package pandox.china.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pandox.china.model.Page;
import pandox.china.model.Phone;
import pandox.china.repo.PageRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PageServiceImpl implements PageService {

	@Autowired
	private PageRepository repo;

    @Autowired
    private CategoryService categoryService;

	private GenericService<Page, Long> getFather() {
		return new GenericServiceImpl(repo);
	}

	@Override
	public Page save(Page entity) {
        Set<Phone> phones = new HashSet<Phone>();
        for (Phone phone : entity.getPhonesForm()) {
            phones.add(phone);
        }
        entity.setPhone(phones);

//        CategoryDTO categoryDTO = categoryService.findOne(entity.getCategory().getId());
//        entity.setCategory(new Category(categoryDTO));

        return repo.save(entity);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        getFather().delete(id);
    }

    @Override
	public List<Page> findAll() {
        //TODO: to implements
		return null;

	}

	@Override
	public Page findOne(Long id) throws IllegalArgumentException {
		return getFather().findOne(id);
	}

	@Override
	public Page findByUrl(String url) {
		return repo.getByUrl(url);
	}

    @Override
    public List<Page> findByUser_Id(Long id) {
        return repo.findByUser_Id(id);
    }
}
