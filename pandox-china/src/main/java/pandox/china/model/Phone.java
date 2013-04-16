package pandox.china.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class Phone extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;
	
	@Column(nullable = false, length = 5)
	private Integer ddi;
	
	@Column(nullable = false)
	private Integer ddd;
	
	@Column(nullable = false)
	private Integer phone;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<User> users;
	
	public Phone() {
		ddi = 55;
	}

	public Phone(Long id) {
		super.setId(id);
		ddi = 55;
	}

	public Integer getDdi() {
		return ddi;
	}

	public void setDdi(Integer ddi) {
		this.ddi = ddi;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
