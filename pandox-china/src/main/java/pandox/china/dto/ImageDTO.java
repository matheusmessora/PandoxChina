package pandox.china.dto;

import org.springframework.beans.BeanUtils;
import pandox.china.model.Image;

public class ImageDTO extends GenericDTO {

    private String name;
    private String type;

    public ImageDTO(){}

    public ImageDTO(Image entity) {
        BeanUtils.copyProperties(entity, this);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImageDTO{");
        sb.append(super.toString());
        sb.append("name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
