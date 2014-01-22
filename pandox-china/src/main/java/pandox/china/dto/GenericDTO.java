package pandox.china.dto;

public class GenericDTO {

    private Long id;

    private boolean selected;

    public GenericDTO(){}

    public GenericDTO(Long id){
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenericDTO{");
        sb.append("id=").append(id);
        sb.append(", selected=").append(selected);
        sb.append('}');
        return sb.toString();
    }
}
