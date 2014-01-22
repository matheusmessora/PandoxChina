package pandox.china.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, PK extends Serializable> {

	T save(T entity);

    void delete(Long id) throws IllegalArgumentException;

    List<T> findAll();
	
	T findOne(PK id) throws IllegalArgumentException;
}
