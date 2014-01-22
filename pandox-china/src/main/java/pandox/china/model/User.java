package pandox.china.model;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import pandox.china.dto.UserDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class User extends GenericEntity {

    private static final long serialVersionUID = 6052205339123414888L;

    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "Nome obrigatório e maior que 3 letras.")
    private String name;

    @Column(nullable = false, unique = true)
    @NotNull(message = "E-mail é obrigatório.")
    @Email(message = "E-mail em formato inválido.")
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Page> pages;

    @Transient
    private Set<GrantedAuthority> roles;


    @JsonManagedReference("user-pages")
    public Set<Page> getPages() {
        return pages;
    }

    {
        roles = new HashSet<GrantedAuthority>();
    }

    public User() {

    }

    public User(Long id) {
        setId(id);
    }

    public User(UserDTO dto){
        BeanUtils.copyProperties(dto, this);
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

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }

    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public void addRole(GrantedAuthority grantedAuthority) {
        this.roles.add(grantedAuthority);
    }

}
