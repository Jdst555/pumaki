package com.mingo.webapp.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import com.mingo.webapp.repository.ProductRepository;
import com.mingo.webapp.model.Product;

@Controller
public class Control {
	
	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping("/")
	public String home(Model model) 
	{
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model)
	{
		List<Product> products = (List<Product>)prodRepo.findAll();
		model.addAttribute("products", products);
		
		return "shop";
	}

}
