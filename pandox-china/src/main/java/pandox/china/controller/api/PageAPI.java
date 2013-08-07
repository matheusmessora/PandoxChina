package pandox.china.controller.api;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pandox.china.controller.BaseController;
import pandox.china.exception.ResourceNotFound;
import pandox.china.model.Page;
import pandox.china.model.Phone;
import pandox.china.model.SocialUser;
import pandox.china.model.User;
import pandox.china.resource.UserRE;
import pandox.china.service.PageService;
import pandox.china.service.SocialUserService;
import pandox.china.service.UserService;
import pandox.china.service.auth.AuthenticationProvider;
import pandox.china.util.ErrorMessage;
import pandox.china.util.SuccessMessage;
import pandox.china.util.ValidadorException;
import pandox.china.util.constants.SocialConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/page")
public class PageAPI extends BaseController {

	private static Logger log = Logger.getLogger(PageAPI.class);

	@Autowired
	private PageService service;

	@Autowired
	private UserService userService;

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


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Page> findAll(
            @RequestParam(value = "user", required = false) Long id,
            @RequestParam(value = "url", required = false) String url) {
        if(id != null){
            List<Page> page = service.findByUser_Id(id);

            if (page == null) {
                throw new ResourceNotFound();
            } else {
                return page;
            }
        }
        if(!StringUtils.isBlank(url)) {
            Page page = service.findByUrl(url);

            if (page == null) {
                throw new ResourceNotFound();
            } else {
                return Arrays.asList(page);
            }
        }
        return service.findAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page> edit(@RequestBody Page page) {
        log.debug("POST. page=" + page);

        User user = userService.findOne(super.getLoggedUser().getId());
        page.setUser(user);
        Phone phone = new Phone();
        phone.setDdi(55);
        phone.setDdd(11L);
        phone.setPhone(999L);
        phone.setPage(page);
        page.getPhones().add(phone);

//            String img = user.getId() + "." + page.getFile().getContentType().split("/")[1];
            page.setImg("1.jpeg");
            page = service.save(page);

            // TODO: Create a service for this
//            try {
//                if (!page.getFile().isEmpty()) {
//                    BufferedImage src = ImageIO.read(new ByteArrayInputStream(page.getFile().getBytes()));
//                    File destination = new File("/opt/resources/img/user-bg/" + img);
//                    page.getFile().transferTo(destination);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            log.debug("POST SUCCESS. page=" + page);
            return new ResponseEntity<Page>(page, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id")Long id) {
        service.delete(id);
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
}
