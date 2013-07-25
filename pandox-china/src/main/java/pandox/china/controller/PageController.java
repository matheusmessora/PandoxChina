package pandox.china.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pandox.china.model.Page;
import pandox.china.model.Phone;
import pandox.china.model.SocialUser;
import pandox.china.model.User;
import pandox.china.service.PageService;
import pandox.china.service.SocialUserService;
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
	private SocialUserService socialUserService;
	
	private SocialUser user;

    @Secured("ROLE_ADMIN")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView edit(Page page, RedirectAttributes redirectAttributes) {
        log.debug("POST. page=" + page);

		user = socialUserService.findOne(super.getLoggedUser().getId());
		page.setSocialUser(user);


        try {
            String img = user.getId() + "." + page.getFile().getContentType().split("/")[1];
            page.setImg(img);
            service.save(page);

            // TODO: Create a service for this
            try {
                if (!page.getFile().isEmpty()) {
                    BufferedImage src = ImageIO.read(new ByteArrayInputStream(page.getFile().getBytes()));
                    File destination = new File("/opt/resources/img/user-bg/" + img);
                    page.getFile().transferTo(destination);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView exceptionWebHandler(MaxUploadSizeExceededException ex, HttpServletRequest request, HttpServletResponse response) {
        return redirectToUserAdmin(1L);
    }
}
