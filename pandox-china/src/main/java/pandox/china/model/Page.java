package pandox.china.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Page extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;

	@Column(nullable = false)
	private String url;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String description;
	
	@ManyToOne
	private User user;
	
	@ManyToMany(mappedBy = "pages", fetch=FetchType.EAGER)
	private Set<Phone> phones;
	
	public Page() {
	}

	public Page(Long id) {
		super.setId(id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
