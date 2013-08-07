package pandox.china.resource;

import org.apache.velocity.runtime.directive.Include;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import pandox.china.model.Page;
import pandox.china.model.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class UserRE extends GenericRE {

    private String name;

    private String email;

    private String password;

    public UserRE(){}

    public UserRE(User orig){
        try {
            String[] ignored = {"password"};
            BeanUtils.copyProperties(orig, this, ignored);
        } catch (BeansException e) {
            e.printStackTrace();
        }
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRE{");
        sb.append(super.toString());
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
