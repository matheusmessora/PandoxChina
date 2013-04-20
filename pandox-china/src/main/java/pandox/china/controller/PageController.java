package pandox.china.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pandox.china.model.Page;
import pandox.china.model.User;
import pandox.china.service.PageService;
import pandox.china.service.UserService;
import pandox.china.util.ErrorMessage;
import pandox.china.util.SuccessMessage;
import pandox.china.util.ValidadorException;

@Controller
@RequestMapping(value = "page")
public class PageController extends BaseController {

	private static Logger log = Logger.getLogger(PageController.class);

	@Autowired
	private PageService service;
	
	@Autowired
	private UserService userService;
	
	private User user;

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView edit(Page page) {

		user = userService.findOne(super.getLoggedUser().getId());
		user.getPages().add(page);
		page.setUser(user);
		page = service.save(page);

		ModelAndView mv = new ModelAndView("user/index");
		mv.addObject("user", user);
		mv.addObject("pages", user.getPages());
		mv.addObject("message", new SuccessMessage("Página cadastrada com sucesso!"));
		return mv;
	}

	@ExceptionHandler(ValidadorException.class)
	public ModelAndView exceptionWebHandler(ValidadorException ex, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("page/index");
		mv.addObject("message", new ErrorMessage(ex.getErros()));
//		mv.addObject("users", service.findAll());
		return mv;
	}

}
