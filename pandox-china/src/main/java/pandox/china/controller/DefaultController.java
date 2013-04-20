package pandox.china.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pandox.china.model.User;
import pandox.china.service.PageService;
import pandox.china.util.ErrorMessage;
import pandox.china.util.ValidadorException;

@Controller
@RequestMapping(value = "/")
public class DefaultController extends BaseController {

	private static Logger log = Logger.getLogger(DefaultController.class);

	@Autowired
	private PageService service;

	private User user;

	@RequestMapping(value = "{url}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("url") String url) {

		ModelAndView mv = new ModelAndView("userPage");
		mv.addObject("page", service.findByUrl(url));

		return mv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionWebHandler(ValidadorException ex, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/index");
		mv.addObject("message", new ErrorMessage(ex.getErros()));
		mv.addObject("users", service.findAll());
		mv.addObject("user", user);
		return mv;
	}
}
