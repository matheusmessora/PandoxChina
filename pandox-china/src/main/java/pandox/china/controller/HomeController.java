package pandox.china.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pandox.china.model.User;
import pandox.china.service.UserService;
import pandox.china.util.ErrorMessage;
import pandox.china.util.ValidadorException;

@Controller
@RequestMapping(value = "/")
public class HomeController extends BaseController {

	private static Logger log = Logger.getLogger(HomeController.class);

	@Autowired
	private UserService service;
	
	private User user;

	
	@RequestMapping(value = "")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/index");
		mv.addObject("users", service.findAll());
		return mv;
	}
	
	@RequestMapping(value = "login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("login");
		return mv;
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
