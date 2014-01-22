package pandox.china.dto;

import org.springframework.beans.BeanUtils;
import pandox.china.model.Quality;

public class QualityDTO extends GenericDTO {

    private String description;

    public QualityDTO() {
    }

    public QualityDTO(Quality entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
