package pandox.china.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.Set;

import javax.persistence.*;
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


	@ManyToOne
	private Page page;

    @JsonBackReference
    public Page getPage() {
        return page;
    }

	public Integer getDdi() {
		return ddi;
	}

	public void setDdi(Integer ddi) {
		this.ddi = ddi;
	}

	public void setPage(Page page) {
		this.page = page;
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
