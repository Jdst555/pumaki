package com.mingo.webapp.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControl {
	
	@GetMapping(value="/login")
	public String login() 
	{
		return "login";
	}

}
