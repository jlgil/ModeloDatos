package com.personal.diccionariobd.modelodatos.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;


//@Configuration
@ControllerAdvice
public class CustomErrorViewResolver implements ErrorViewResolver {
	
	private static final Logger log = LoggerFactory.getLogger(CustomErrorViewResolver.class);

	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request,
			HttpStatus status, Map<String, Object> model) {
	     Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
	        String exceptionMessage = getExceptionMessage(throwable, statusCode);
	        
	        log.info("Mensaje de Error: " + exceptionMessage);
	      	        
	        String trace = null;
	        StringWriter s = null;
	        
	        if (throwable == null){
	        	trace = exceptionMessage;
	        }else{
		        s = new StringWriter();
		        throwable.printStackTrace(new PrintWriter(s));
		        trace = s.toString();
		        log.info("Por el error personalizado Clase:: " + throwable.getClass().getName());
	        }
	        

	        


	        ModelAndView modelv = new ModelAndView("error");
	        modelv.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
	        modelv.addObject("message", exceptionMessage);
	        modelv.addObject("trace", trace);
	        return modelv;

	}

	
    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return throwable.getMessage();
        }

        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }


    @ExceptionHandler(value={ConstraintViolationException.class})
   @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleValidationFailure(ConstraintViolationException ex) {

        StringBuilder messages = new StringBuilder();
        log.info("Por el error personalizado ENTRO EL EN EXCEPTIONHANDERL");

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            messages.append(violation.getMessage() + "\n");
        }

        return messages.toString();
    }
    
    @ExceptionHandler(value={NestedServletException.class})
   @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleOtro(NestedServletException ex) {

        StringBuilder messages = new StringBuilder();
        log.info("Por el error personalizado OTROOO EL EN EXCEPTIONHANDERL");

        

        return ex.getMessage();
    }


}
