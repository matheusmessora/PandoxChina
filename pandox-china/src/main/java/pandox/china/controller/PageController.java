package pandox.china.controller;

import java.util.HashSet;
import java.util.Set;

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

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pandox.china.model.Page;
import pandox.china.model.Phone;
import pandox.china.model.User;
import pandox.china.service.PageService;
import pandox.china.service.UserService;
import pandox.china.util.ErrorMessage;
import pandox.china.util.SuccessMessage;
import pandox.china.util.ValidadorException;

@Controller
@RequestMapping("page")
public class PageController extends BaseController {

	private static Logger log = Logger.getLogger(PageController.class);

	@Autowired
	private PageService service;
	
	@Autowired
	private UserService userService;
	
	private User user;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView edit(Page page, RedirectAttributes redirectAttributes) {
        log.debug("POST. page=" + page);

		user = userService.findOne(super.getLoggedUser().getId());
		page.setUser(user);


        try {
            service.save(page);
        } catch (ValidadorException ex) {
            return generateFriendlyBadRequest(ex, user.getId(), redirectAttributes);
        }

        log.debug("POST SUCCESS. page=" + page);
        redirectAttributes.addFlashAttribute("message", new SuccessMessage("Página cadastrada com sucesso!"));
        return redirectToUserAdmin(user.getId());
    }

    private ModelAndView redirectToUserAdmin(Long id){
        return new ModelAndView("redirect:/usuario/" + id + "/admin");
    }

    private ModelAndView generateFriendlyBadRequest(ValidadorException ex, Long id, RedirectAttributes attributes){
        attributes.addFlashAttribute("message", new SuccessMessage("Página cadastrada com sucesso!"));
        return redirectToUserAdmin(id);
    }



	@ExceptionHandler(ValidadorException.class)
	public ModelAndView exceptionWebHandler(ValidadorException ex, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("page/index");
		mv.addObject("message", new ErrorMessage(ex.getErros()));
//		mv.addObject("users", service.findAll());
		return mv;
	}
}
