package pandox.china.controller.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import pandox.china.controller.BaseController;
import pandox.china.dto.UserDTO;
import pandox.china.exception.ResourceNotFound;
import pandox.china.model.User;
import pandox.china.service.UserService;
import pandox.china.service.auth.AuthenticationProvider;
import pandox.china.util.ValidadorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserAPI extends BaseController {

	private static Logger log = Logger.getLogger(UserAPI.class);

	@Autowired
	private UserService service;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDTO create(@RequestBody UserDTO data) {
        User user = service.save(new User(data));
        doAutoLogin(user);


        data.setId(user.getId());

        return data;
    }

    @RequestMapping(value = "passwd", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDTO changePassword(@RequestBody UserDTO data) {
        data.validateForChangePwd();

        User user = getLoggedUser();
        user = service.findOne(user.getId());
        if (user.getPassword().equals(data.getOldPassword())) {
            user.setPassword(data.getPassword());
            user = service.save(user);
            return new UserDTO(user);
        }else {
            throw new IllegalArgumentException("Senhas não conferem");
        }
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void admin() {
        log.info(RequestContextHolder.getRequestAttributes().getSessionId());
//        SecurityContextHolder.getContext();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO data) {
        User user = service.findByEmailAndPassword(data.getEmail(), data.getPassword());
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
        }else {
            doAutoLogin(user);
            return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
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
