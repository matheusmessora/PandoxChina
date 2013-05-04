package pandox.china.model;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table
public class User extends GenericEntity {

    private static final long serialVersionUID = 6052205339123414888L;

    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "Nome obrigatório e maior que 3 letras.")
    private String name;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 50, message = "E-mail é obrigatório.")
    @Email(message = "E-mail em formato inválido.")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Senha obrigatória.")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Page> pages;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Phone> phones;

    @Transient
    private Set<GrantedAuthority> roles = new TreeSet<GrantedAuthority>();

    public User() {
        roles = new TreeSet<GrantedAuthority>();
    }

    public User(Long id) {
        super.setId(id);
        roles = new TreeSet<GrantedAuthority>();
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

    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }

}
