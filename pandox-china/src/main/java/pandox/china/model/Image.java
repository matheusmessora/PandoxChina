package pandox.china.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Image extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;
	
	@Column(nullable = false)
	private String name;

    @Column
    private String type;


	@ManyToOne
	private Page page;

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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
