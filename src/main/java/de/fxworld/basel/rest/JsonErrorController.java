package de.fxworld.basel.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class JsonErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    public ErrorJson error(WebRequest webRequest) {

		Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(webRequest, false);
    	
        return new ErrorJson(0, errorAttributesMap);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}