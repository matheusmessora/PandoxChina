package pandox.china.controller.api;

import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import pandox.china.controller.BaseController;
import pandox.china.exception.ResourceNotFound;
import pandox.china.model.SocialUser;
import pandox.china.model.User;
import pandox.china.resource.UserRE;
import pandox.china.service.SocialUserService;
import pandox.china.service.UserService;
import pandox.china.service.auth.AuthenticationProvider;
import pandox.china.util.ValidadorException;
import pandox.china.util.constants.SocialConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserAPI extends BaseController {

	private static Logger log = Logger.getLogger(UserAPI.class);

	@Autowired
	private UserService service;

    @RequestMapping(value = "", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User create(@RequestBody User data) {
        User user = service.save(data);
        doAutoLogin(user);

        return user;
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void admin() {
        log.info(RequestContextHolder.getRequestAttributes().getSessionId());
//        SecurityContextHolder.getContext();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserRE> login(@RequestBody User data) {
        User user = service.findByEmailAndPassword(data.getEmail(), data.getPassword());
        if (user == null) {
            return new ResponseEntity<UserRE>(HttpStatus.UNAUTHORIZED);
        }else {
            doAutoLogin(user);
            return new ResponseEntity<UserRE>(new UserRE(user), HttpStatus.OK);
        }
    }


    private void doAutoLogin(User user) {
        AuthenticationProvider authenticationProvider = new AuthenticationProvider();
        Authentication authenticationToken = authenticationProvider.createUserAndTokenAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @ExceptionHandler(ValidadorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> exceptionWebHandler(ValidadorException ex, HttpServletRequest request, HttpServletResponse response) {
        return ex.getErros();
    }


	@ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Recurso não encontrado.")
	public void resourceNotFoundHandler(ResourceNotFound ex, HttpServletResponse response) {
        log.info("Content Not-Found");
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String genericHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return ex.getLocalizedMessage();
    }
}
