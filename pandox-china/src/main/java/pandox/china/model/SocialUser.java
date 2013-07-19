package pandox.china.model;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class SocialUser extends GenericEntity {

    private static final long serialVersionUID = 6052205339123414888L;

    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "Nome obrigatório e maior que 3 letras.")
    private String name;

    @Column(nullable = false, unique = true)
    @NotNull(message = "UID obrigatório.")
    private Long uid;

    @Column(nullable = false, unique = true)
    @NotNull(message = "AccessToken é obrigatório.")
    private String accessToken;

    @OneToMany(mappedBy = "socialUser", fetch = FetchType.EAGER)
    private Set<Page> pages;

    @Transient
    private Set<GrantedAuthority> roles;

    public SocialUser() {
        roles = new HashSet<GrantedAuthority>();
    }

    public SocialUser(Long id) {
        super.setId(id);
        roles = new HashSet<GrantedAuthority>();
    }

    @JsonManagedReference("socialUser-page")
    public Set<Page> getPages() {
        return pages;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SocialUser{");
        sb.append(super.toString());
        sb.append("name='").append(name).append('\'');
        sb.append(", uid=").append(uid);
        sb.append(", accessToken=").append(accessToken);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
