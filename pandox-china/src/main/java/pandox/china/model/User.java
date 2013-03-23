package pandox.china.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table
public class User extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;

	@Column(nullable = false)
	@Size(min=3, max=50, message="Nome muito éééé...")
	private String name;

	@Column(nullable = false, unique = true)
	@Size(min=3, max=50, message="E-mail é obrigatório.")
	@Email(message="E-mail em formato inválido.")
	private String email;

	@Column(nullable = false)
	private String password;

	@OneToMany
	private Set<Page> pages;

	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private Set<Phone> phones;

	public User() {
	}

	public User(Long id) {
		super.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "User [getId()=" + getId() + ", name=" + name + ", email=" + email + "]";
	}

}
