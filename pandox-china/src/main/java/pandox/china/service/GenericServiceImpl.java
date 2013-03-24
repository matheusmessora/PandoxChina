package pandox.china.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pandox.china.util.ValidadorException;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
@Transactional(readOnly = true)
public abstract class GenericServiceImpl<T, PK extends Serializable, DAO extends CrudRepository> implements GenericService<T, PK> {
	
	@Autowired
	protected DAO dao;

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
