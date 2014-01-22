package pandox.china.controller.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pandox.china.controller.BaseController;
import pandox.china.dto.QualityDTO;
import pandox.china.exception.ResourceNotFound;
import pandox.china.service.QualityService;
import pandox.china.util.ValidadorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/quality")
public class QualityAPI extends BaseController {

	private static Logger log = Logger.getLogger(QualityAPI.class);

	@Autowired
	private QualityService service;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<QualityDTO> findAll() {
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
