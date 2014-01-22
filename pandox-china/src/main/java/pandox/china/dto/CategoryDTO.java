package pandox.china.dto;

import org.springframework.beans.BeanUtils;
import pandox.china.model.Category;

public class CategoryDTO extends GenericDTO {

    private String name;

    public CategoryDTO(){}

    public CategoryDTO(Category category) {
        BeanUtils.copyProperties(category, this);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
