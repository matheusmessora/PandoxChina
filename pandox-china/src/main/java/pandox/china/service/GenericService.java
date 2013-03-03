package pandox.china.service;

import java.io.Serializable;


public interface GenericService<T, PK extends Serializable> {

	T save(T entity);
	
	T findAll();
}
