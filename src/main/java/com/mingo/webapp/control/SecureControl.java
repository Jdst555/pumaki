package com.mingo.webapp.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mingo.webapp.model.Order;
import com.mingo.webapp.repository.UserRepository;
import com.mingo.webapp.security.Registration;

@Controller
@RequestMapping("/register")
public class SecureControl {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	
	
	@GetMapping
	public String registration() 
	{
		return "register";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String sendRegistration(@ModelAttribute Registration reg) 
	{
		userRepo.save(reg.toUser(passEncoder));
		return "index";
	}

}
