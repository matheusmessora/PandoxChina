package pandox.china.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GenericServiceImpl<T, PK extends Serializable, DAO extends CrudRepository> implements GenericService<T, PK> {
	
	@Autowired
	private DAO dao;

	@Override
	public T save(T entity) {
		return (T) dao.save(entity);
	}

	@Override
	public T findAll() {
		return (T) dao.findAll();
	}

	@Override
	public T findOne(PK id) throws IllegalArgumentException {
		if(id == null){
			throw new IllegalArgumentException();
		}else {
			return (T) dao.findOne(id);
		}
	}

}
