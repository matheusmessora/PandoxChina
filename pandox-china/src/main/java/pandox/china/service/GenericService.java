package pandox.china.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

public interface GenericService<T, PK extends Serializable> {

	T save(T entity);
	
	T findAll();
	
	T findOne(PK id) throws IllegalArgumentException;
}
