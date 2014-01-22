package pandox.china.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pandox.china.dto.QualityDTO;
import pandox.china.model.Quality;
import pandox.china.repo.QualityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class QualityServiceImpl implements QualityService {

	@Autowired
	private QualityRepository repo;

	private GenericService<QualityDTO, Long> getFather() {
		return new GenericServiceImpl(repo);
	}

    @Override
    public QualityDTO save(QualityDTO entity) {
        return null;
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        getFather().delete(id);
    }

    @Override
	public List<QualityDTO> findAll() {
        Iterable<Quality> entityList = repo.findAll();

        List<QualityDTO> dtoList = new ArrayList<QualityDTO>();
        for (Quality quality : entityList) {
            dtoList.add(new QualityDTO(quality));
        }

        return dtoList;
	}

	@Override
	public QualityDTO findOne(Long id) throws IllegalArgumentException {
        return new QualityDTO();
	}
}
