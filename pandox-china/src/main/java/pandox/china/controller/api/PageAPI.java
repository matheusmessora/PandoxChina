package pandox.china.controller.api;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pandox.china.controller.BaseController;
import pandox.china.dto.PageDTO;
import pandox.china.exception.ResourceNotFound;
import pandox.china.model.Page;
import pandox.china.model.User;
import pandox.china.service.PageService;
import pandox.china.service.UserService;
import pandox.china.util.ValidadorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    public PageDTO find(@PathVariable("id") Long id) {
        Page page = service.findOne(id);

        if(page == null) {
            throw new ResourceNotFound();
        }else {
            return new PageDTO(page);
        }
    }


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<PageDTO> findAll(@RequestParam(value = "user", required = false) Long userId, @RequestParam(value = "url", required = false) String url) {
        if(userId != null){
            List<Page> pagesList = service.findByUser_Id(userId);

            if (pagesList.isEmpty()) {
                throw new ResourceNotFound();
            } else {
                return Arrays.asList(new PageDTO(pagesList.get(0)));
            }
        }
        if(!StringUtils.isBlank(url)) {
            Page page = service.findByUrl(url);

            if (page == null) {
                throw new ResourceNotFound();
            } else {
                return Arrays.asList(new PageDTO(page));
            }
        }
        ArrayList<Page> all = (ArrayList<Page>) service.findAll();

        return Arrays.asList(new PageDTO(all.get(0)));
    }


    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page persist(@RequestBody PageDTO pageDTO) {
        log.debug("POST. page=" + pageDTO);

        User user = userService.findOne(super.getLoggedUser().getId());
        Page page = new Page(pageDTO);
        page.setUser(user);
//        for (Phone phone : pageDTO.getPhonesForm()) {
//            phone.setDdi(55);
//            page.addPhone(phone);
//        }

//            String img = user.getId() + "." + page.getFile().getContentType().split("/")[1];
        page.setMainColor(new Date().toString());
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
        return page;
//            return new ResponseEntity<Page>(page, HttpStatus.OK);
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
