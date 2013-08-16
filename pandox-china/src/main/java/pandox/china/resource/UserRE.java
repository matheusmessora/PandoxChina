package pandox.china.resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import pandox.china.model.User;


public class UserRE extends GenericRE {

    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
    private String oldPassword;

    public UserRE(){}

    public UserRE(User orig){
        try {
            String[] ignored = {"password"};
            BeanUtils.copyProperties(orig, this, ignored);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    public void validateForChangePwd(){
        if(StringUtils.isEmpty(getPassword()) || StringUtils.isEmpty(getPasswordConfirm()) || StringUtils.isEmpty(getOldPassword())) {
            throw new IllegalArgumentException("Senhas não conferem.");
        }

        if(!getPassword().equals(getPasswordConfirm())){
            throw new IllegalArgumentException("Senhas não conferem.");
        }
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
