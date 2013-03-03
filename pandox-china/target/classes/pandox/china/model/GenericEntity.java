package pandox.china.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntity implements Serializable {

	private static final long serialVersionUID = 8087143779419910194L;

	@Id
	@GeneratedValue
	private Long id;
	
//	@Column(nullable=false)
//	@Enumerated(EnumType.ORDINAL) 
//	private StatusEnum status;
	
	private transient boolean checado;

	public boolean isChecado() {
		return checado;
	}

	public void setChecado(boolean checado) {
		this.checado = checado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericEntity other = (GenericEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/**
	 * Detects if the entity has an ID value.
	 * @return	false : null
	 * 			false : -1
	 * 			true	: 1
	 */
	public boolean hasId(){
		if(id == null || id.longValue() < 0){
			return false;
		}else {
			return true;
		}
	}
	
}
