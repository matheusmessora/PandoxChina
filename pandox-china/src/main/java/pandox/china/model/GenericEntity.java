package pandox.china.model;

import java.io.Serializable;

import javax.persistence.*;

@MappedSuperclass
public abstract class GenericEntity implements Serializable {

	private static final long serialVersionUID = 8087143779419910194L;

	@Id
	@GeneratedValue
	private Long id;
	
//	@Column(nullable=false)
//	@Enumerated(EnumType.ORDINAL) 
//	private StatusEnum status;

    @Transient
	private boolean checked;

    @Transient
    private String message;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenericEntity{");
        sb.append("id=").append(id);
        sb.append(", checked=").append(checked);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
