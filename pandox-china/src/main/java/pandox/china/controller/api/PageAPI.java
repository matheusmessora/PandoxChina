package pandox.china.controller.api;

import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pandox.china.controller.BaseController;
import pandox.china.exception.ResourceNotFound;
import pandox.china.model.Page;
import pandox.china.model.SocialUser;
import pandox.china.service.PageService;
import pandox.china.service.SocialUserService;
import pandox.china.service.auth.AuthenticationProvider;
import pandox.china.util.ErrorMessage;
import pandox.china.util.ValidadorException;
import pandox.china.util.constants.SocialConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/page")
public class PageAPI extends BaseController {

	private static Logger log = Logger.getLogger(PageAPI.class);

	@Autowired
	private PageService service;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Page find(@PathVariable("id")Long id) {
        Page page = service.findOne(id);

        if(page == null) {
            throw new ResourceNotFound();
        }else {
            return page;
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id")Long id) {
        service.delete(id);
    }

	@ExceptionHandler(ValidadorException.class)
	public ModelAndView exceptionWebHandler(ValidadorException ex, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/index");
		mv.addObject("message", new ErrorMessage(ex.getErros()));
		mv.addObject("users", service.findAll());
		return mv;
	}

	@ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Recurso não encontrado.")
	public void resourceNotFoundHandler(ResourceNotFound ex, HttpServletResponse response) {
        log.info("Content Not-Found");
    }
}
