package pandox.china.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pandox.china.model.Page;
import pandox.china.model.Phone;
import pandox.china.repo.PageRepository;

@Service
@Transactional(readOnly = true)
public class PageServiceImpl implements PageService {

	@Autowired
	private PageRepository repo;

	private GenericService<Page, Long> getFather() {
		return new GenericServiceImpl(repo);
	}

	@Override
	public Page save(Page entity) {
        Set<Phone> phones = new HashSet<Phone>();
        for (Phone phone : entity.getPhonesForm()) {
            phones.add(phone);
        }
        entity.setPhones(phones);

		return getFather().save(entity);
	}

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        getFather().delete(id);
    }

    @Override
	public ArrayList<Page> findAll() {
		return getFather().findAll();
	}

	@Override
	public Page findOne(Long id) throws IllegalArgumentException {
		return getFather().findOne(id);
	}

	@Override
	public Page findByUrl(String url) {
		return repo.getByUrl(url);
	}
}
