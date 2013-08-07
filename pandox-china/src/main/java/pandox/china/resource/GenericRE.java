package pandox.china.resource;

import org.apache.commons.beanutils.BeanUtils;
import pandox.china.model.User;

import java.util.List;

public class GenericRE {

    private Long id;

    public GenericRE(){}

    public GenericRE(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenericRE{");
        sb.append(super.toString());
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
