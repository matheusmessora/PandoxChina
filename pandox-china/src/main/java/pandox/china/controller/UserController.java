package pandox.china.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pandox.china.model.Phone;
import pandox.china.model.User;
import pandox.china.service.UserService;

@Controller
@RequestMapping(value = "/usuario")
public class UserController extends BaseController {

	private static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService service;

	@RequestMapping(value = "")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/index");
		mv.addObject("users", service.findAll()); 
		return mv;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView post(User user, Phone phone) {
		user = service.save(user);

		ModelAndView mv = new ModelAndView("");
		mv.setViewName("user/index");
		mv.addObject(user);
		mv.addObject("users", service.findAll());
		return mv;
	}
}
