package pandox.china.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
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
