package pandox.china.controller.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pandox.china.controller.BaseController;
import pandox.china.dto.CategoryDTO;
import pandox.china.exception.ResourceNotFound;
import pandox.china.service.CategoryService;
import pandox.china.util.ValidadorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryAPI extends BaseController {

	private static Logger log = Logger.getLogger(CategoryAPI.class);

	@Autowired
	private CategoryService service;

//    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
//    @ResponseBody
//    public Page find(@PathVariable("id")Long id) {
//        Page page = service.findOne(id);
//
//        if(page == null) {
//            throw new ResourceNotFound();
//        }else {
//            return page;
//        }
//    }


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<CategoryDTO> findAll() {
        return service.findAll();
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
