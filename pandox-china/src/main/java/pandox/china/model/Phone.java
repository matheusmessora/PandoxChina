package pandox.china.model;

import org.codehaus.jackson.annotate.JsonBackReference;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table
public class Phone extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;
	
	@Column(nullable = false, length = 5)
	private Integer ddi;
	
	@Column
//	@Min(value=2, message="Informe o DDD.")
//	@Max(value=2, message="Informe o DDD.")
	private Long ddd;
	
	@Column
//	@Min(value=8, message="Informe o telefone.")
//	@Max(value=9, message="Informe o telefone.")
	private Long phone;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<User> users;


	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Page> pages;

    @JsonBackReference("phone-page")
    public Set<Page> getPages() {
        return pages;
    }

	public Integer getDdi() {
		return ddi;
	}

	public void setDdi(Integer ddi) {
		this.ddi = ddi;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Long getDdd() {
		return ddd;
	}

	public void setDdd(Long ddd) {
		this.ddd = ddd;
	}

}
