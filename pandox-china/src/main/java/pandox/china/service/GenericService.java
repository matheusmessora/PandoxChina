package pandox.china.service;

import java.io.Serializable;
import java.util.ArrayList;

public interface GenericService<T, PK extends Serializable> {

	T save(T entity);

    void delete(Long id) throws IllegalArgumentException;

    ArrayList<T> findAll();
	
	T findOne(PK id) throws IllegalArgumentException;
}
