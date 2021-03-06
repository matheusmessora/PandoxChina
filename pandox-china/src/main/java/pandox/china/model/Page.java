package pandox.china.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.springframework.util.AutoPopulatingList;
import pandox.china.dto.CategoryDTO;
import pandox.china.dto.PageDTO;
import pandox.china.dto.QualityDTO;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Page extends GenericEntity {

	private static final long serialVersionUID = 6052205339123414888L;

	@Column(nullable = false, unique = true)
	@Size(min = 3, max = 50, message = "Nome é obrigatório.")
	private String url;

    @Column(nullable = false)
    @Size(min = 3, max = 60, message = "Título é obrigatório.")
    private String name;

	@Column
	private String description;

    @Column
	private String mainColor;

	@ManyToOne
	private SocialUser socialUser;

	@ManyToOne
	private User user;

	@ManyToOne
	private Category category;

    @Column
    @Email
    private String email;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Phone> phones;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Quality> qualities;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "page", cascade = CascadeType.ALL)
    private Set<Image> images;

    // ---- FORMs Attributes -----
	@Transient
	private List<Phone> phonesForm;

    {
        phonesForm = new AutoPopulatingList<Phone>(Phone.class);
    }

    public Page() {

    }

    public Page(Long id) {
        setId(id);
    }

    public Page(PageDTO dto) {
        setId(dto.getId());
        setDescription(dto.getDescription());
        setEmail(dto.getEmail());
        setName(dto.getName());
        setUrl(dto.getUrl());

        CategoryDTO categoryDTO = dto.getCategory();
        if (categoryDTO != null) {
            Long categoryId = categoryDTO.getId();
            if (!(categoryId == null || categoryId.equals(0))) {
                Category categoryEntity = new Category(categoryId);
                setCategory(categoryEntity);
            }
        }

        for (QualityDTO qualityDTO : dto.getQualityList()) {
            addQuality(new Quality(qualityDTO));
        }

    }


    public void addPhone(Phone phone){
        if(this.phones == null) {
            this.phones = new HashSet<Phone>();
        }
        this.phones.add(phone);
    }

    public void addQuality(Quality quality){
        if(this.qualities == null) {
            this.qualities = new HashSet<Quality>();
        }
        this.qualities.add(quality);
    }


    @JsonBackReference
    public SocialUser getSocialUser() {
        return socialUser;
    }

    @JsonBackReference("user-pages")
    public User getUser() {
        return user;
    }

    @JsonManagedReference
    public Set<Phone> getPhones() {
        return phones;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

	public void setSocialUser(SocialUser user) {
		this.socialUser = user;
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

	public void setPhone(Set<Phone> phones) {
		this.phones = phones;
	}

	public List<Phone> getPhonesForm() {
		return phonesForm;
	}

	public void setPhonesForm(List<Phone> phonesForm) {
		this.phonesForm = phonesForm;
	}

    public Set<Quality> getQualities() {
        return qualities;
    }

    public void setQualities(Set<Quality> qualities) {
        this.qualities = qualities;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Page{");
        sb.append(super.toString());
        sb.append("url='").append(url).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", mainColor='").append(mainColor).append('\'');
        sb.append(", socialUser=").append(socialUser);
        sb.append(", user=").append(user);
        sb.append(", category=").append(category);
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
