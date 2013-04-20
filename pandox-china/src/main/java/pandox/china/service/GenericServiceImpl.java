package pandox.china.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pandox.china.util.ValidadorException;

//@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
//@Transactional(readOnly = true)
public class GenericServiceImpl<T, PK extends Serializable> implements GenericService<T, PK> {
	
	protected CrudRepository<T, Serializable> dao;
	
	public GenericServiceImpl(CrudRepository dao){
		this.dao = dao;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public T save(T entity) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);

		if(constraintViolations.size() > 0){
			List<String> errors = new ArrayList<String>();
			for (ConstraintViolation<T> violation : constraintViolations) {
				errors.add(violation.getMessage());
			}
			throw new ValidadorException(errors);
		}
		
		return (T) dao.save(entity);
	}

	@Override
	public ArrayList<T> findAll() {
		return (ArrayList<T>) dao.findAll();
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
