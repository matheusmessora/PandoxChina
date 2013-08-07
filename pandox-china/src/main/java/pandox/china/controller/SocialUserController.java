package pandox.china.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pandox.china.model.Phone;
import pandox.china.model.SocialUser;
import pandox.china.model.User;
import pandox.china.service.SocialUserService;
import pandox.china.service.auth.AuthenticationProvider;
import pandox.china.util.ErrorMessage;
import pandox.china.util.SuccessMessage;
import pandox.china.util.ValidadorException;

@Controller
@RequestMapping("/usuario")
public class SocialUserController extends BaseController {

	private static Logger log = Logger.getLogger(SocialUserController.class);

	@Autowired
	private SocialUserService service;

	private SocialUser user;

	@Secured("ROLE_ADMIN")
    @RequestMapping(value = "{id}/admin", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public ModelAndView index(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("user/index");
		SocialUser user = service.findOne(id);
		mv.addObject("user", user);
		mv.addObject("pages", user.getPages());
		return mv;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id") Long id) {
		SocialUser user = service.findOne(id);
		if (user == null) {
			ModelAndView mv = new ModelAndView("user/index");
			mv.addObject("users", service.findAll());
			mv.addObject("message", new ErrorMessage("Usuário não encontrado!"));
			return mv;
		}

		ModelAndView mv = new ModelAndView("user/show");
		mv.addObject(user);
		return mv;
	}


//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ModelAndView login(User user) {
//		this.user = user;
//		user = service.findByEmailAndPassword(user.getEmail(), user.getPassword());
//
//		ModelAndView mv = new ModelAndView("user/index");
//		mv.addObject("users", service.findAll());
//		if (user != null)
//			mv.addObject("message", new SuccessMessage("Usuário ENCONTRADO!"));
//		return mv;
//	}

//	@Secured("ADMIN")
//	@RequestMapping(value = "{id}", method = RequestMethod.POST)
//	public ModelAndView edit(@PathVariable("id") Long id, User user) {
//		ModelAndView mv = new ModelAndView("user/index");
//
//		try {
//			user = service.save(user);
//		} catch (Exception e) {
//			mv.addObject("message", new ErrorMessage("Usuário não encontrado!"));
//		}
//
//		mv.addObject("users", service.findAll());
//		mv.addObject("message", new SuccessMessage("Usuário cadastrado com sucesso!"));
//		return mv;
//	}

//	@RequestMapping(value = "", method = RequestMethod.POST)
//	public ModelAndView create(User user, Phone phone) {
//		this.user = user;
//		user = service.save(user);
//
//		doAutoLogin(user);
//		ModelAndView mv = new ModelAndView("redirect:/usuario/" + user.getId() + "/admin");
//		mv.addObject("users", service.findAll());
//		mv.addObject("message", new SuccessMessage("Usuário cadastrado com sucesso!"));
//		return mv;
//	}

	private void doAutoLogin(User user) {
		AuthenticationProvider authenticationProvider = new AuthenticationProvider();
		Authentication authenticationToken = authenticationProvider.createUserAndTokenAuthentication(user);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	@ExceptionHandler(ValidadorException.class)
	public ModelAndView exceptionWebHandler(ValidadorException ex, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/index");
		mv.addObject("message", new ErrorMessage(ex.getErros()));
		mv.addObject("users", service.findAll());
		mv.addObject("user", user);
		return mv;
	}
}
