package pandox.china.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.util.AutoPopulatingList;

@Entity
@Table
public class Page extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;

	@Column(nullable = false, unique = true)
	@Size(min = 3, max = 50, message = "Nome é obrigatório.")
	private String url;

	@Column
	private String description;

	@Column(nullable = false)
	@Size(min = 3, max = 10, message = "Cor é obrigatório.")
	private String mainColor;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

    @Column
    @Email
    private String email;

    @Column
    private String img;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Phone> phones;
	
	// ---- FORMs Attributes -----
	@Transient
	private List<Phone> phonesForm;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Page{");
        sb.append(super.toString());
        sb.append("img='").append(img).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", mainColor='").append(mainColor).append('\'');
        sb.append(", user=").append(user);
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Page() {
		phonesForm = new AutoPopulatingList<Phone>(Phone.class);
	}

	public Page(Long id) {
		super.setId(id);
		phonesForm = new AutoPopulatingList<Phone>(Phone.class);
	}

	public String getDescription() {
		return description;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public List<Phone> getPhonesForm() {
		return phonesForm;
	}

	public void setPhonesForm(List<Phone> phonesForm) {
		this.phonesForm = phonesForm;
	}
}
