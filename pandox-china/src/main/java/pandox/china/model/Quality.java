package pandox.china.model;


import pandox.china.dto.QualityDTO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Quality extends GenericEntity  {

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "qualities")
    private Set<Page> page;

    public Quality(){}

    public Quality(QualityDTO dto) {
        this.description = dto.getDescription();
        this.setId(dto.getId());

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Page> getPage() {
        return page;
    }

    public void setPage(Set<Page> page) {
        this.page = page;
    }
}
