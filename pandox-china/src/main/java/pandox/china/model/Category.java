package pandox.china.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import pandox.china.dto.CategoryDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Category extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;

    @Column(nullable = false)
    private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	private Set<Page> pages;

	// ---- FORMs Attributes -----
	@Transient
	private List<Page> pagesForm;


    public Category(){}

    public Category(Long id) {
        setId(id);
    }

    public Category(CategoryDTO categoryDTO) {
        setId(categoryDTO.getId());
        setName(categoryDTO.getName());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    public List<Page> getPagesForm() {
        return pagesForm;
    }

    public void setPagesForm(List<Page> pagesForm) {
        this.pagesForm = pagesForm;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
