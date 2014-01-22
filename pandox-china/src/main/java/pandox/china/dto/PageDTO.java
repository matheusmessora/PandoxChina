package pandox.china.dto;

import pandox.china.model.Image;
import pandox.china.model.Page;
import pandox.china.model.Quality;

import java.util.ArrayList;
import java.util.List;

public class PageDTO extends GenericDTO {

    private String url;
    private String name;
    private String description;
    private String email;

    private CategoryDTO category;

    private List<QualityDTO> qualityList;
    private List<ImageDTO> imageList;

    {
        qualityList = new ArrayList<QualityDTO>();
        category = new CategoryDTO();
        imageList = new ArrayList<ImageDTO>();
    }

//    private List<Phone> phonesForm;
//    private List<Quality> qualidadesForm;
//    private CommonsMultipartFile file;

//    {
//        phonesForm = new AutoPopulatingList<Phone>(Phone.class);
//        qualidadesForm = new AutoPopulatingList<Quality>(Quality.class);
//    }

    public PageDTO() {
    }

    public PageDTO(Page entity){
        setId(entity.getId());
        setDescription(entity.getDescription());
        setEmail(entity.getEmail());
        setName(entity.getName());
        setUrl(entity.getUrl());

        setCategory(new CategoryDTO(entity.getCategory()));

        for (Quality quality : entity.getQualities()) {
            qualityList.add(new QualityDTO(quality));
        }

        for (Image image : entity.getImages()) {
            imageList.add(new ImageDTO(image));
        }
    }


    public List<ImageDTO> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageDTO> imageList) {
        this.imageList = imageList;
    }

    public List<QualityDTO> getQualityList() {
        return qualityList;
    }

    public void setQualityList(List<QualityDTO> qualityList) {
        this.qualityList = qualityList;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
