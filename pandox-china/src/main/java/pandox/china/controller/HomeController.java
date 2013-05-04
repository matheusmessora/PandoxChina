package pandox.china.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pandox.china.model.User;
import pandox.china.service.UserService;
import pandox.china.util.ErrorMessage;
import pandox.china.util.ValidadorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController extends BaseController {

    private static Logger log = Logger.getLogger(HomeController.class);
    @Autowired
    private UserService service;
    private User user;

    @RequestMapping(value = "")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        // If User is Logged in
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                Object securityUser = authentication.getPrincipal();
                if (securityUser != null && securityUser instanceof User) {
                    User user = (User) securityUser;
                    return new ModelAndView("redirect:/usuario/" + user.getId() + "/admin");
                }
            }
        }
        // Show HomePage
        ModelAndView mv = new ModelAndView("home");
        log.debug("OLA MUNDO JRebel!");

        return mv;
    }

    @RequestMapping(value = "cadastrar")
    public ModelAndView cadastrar(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("signup");
        return mv;
    }

    @RequestMapping(value = "login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("login");
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
