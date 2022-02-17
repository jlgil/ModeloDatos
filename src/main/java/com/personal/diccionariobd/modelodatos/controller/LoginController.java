package com.personal.diccionariobd.modelodatos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login")
    public String getLogin(){
		log.info("Controller Login...");
        return "login";
    }
	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("errors", true);
		
		log.info("Controller Login-Error...");
		return "login";
	}
	


}
