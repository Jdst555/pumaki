package com.mingo.webapp.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.mingo.webapp.repository.OrderRepository;
import com.mingo.webapp.repository.ProductRepository;
import com.mingo.webapp.model.Order;
import com.mingo.webapp.model.Product;

@Controller
public class Control {
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private OrderRepository ordRepo;
	
	@GetMapping(value= {"", "/", "index"})
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
	
	/**************Mapeo de URLs administrativas*************/
	
	
	//Recupera los productos registrados en la BD y los presenta una tabla
	@GetMapping("/products")
	public String products(Model model) 
	{
		List<Product> products = (List<Product>)prodRepo.findAll();
		model.addAttribute("products", products);
		return "products";
		
	}
	
	//Presenta una interface para añadir un nuevo producto a la BD
	@GetMapping("/prod_n")
	public String prod_n() 
	{
		return "prod_n";
	}
	
	//Añade un nuevo producto a la BD.
	@RequestMapping(value= "new_p", method = RequestMethod.POST)
	public String prod_n(@ModelAttribute Product product) 
	{
		
		System.out.println(product);
		prodRepo.save(product);
		return "prod_n";
	}
	
	//Recupera las órdenes registradas en la BD y las presenta en una tabla
	@GetMapping("/orders")
	public String orders(Model model) 
	{
		List<Order> orders = (List<Order>)ordRepo.findAll();
		model.addAttribute("orders", orders);
		return "orders";
	}
	
	//Presenta los detalles de una órden específica
	@GetMapping("/detail")
	
	public void detail(@RequestParam String id) 
	{
		System.out.println("order id es: " + id);
	}
	
	//Presenta una interface para añadir manualmente una nueva orden a la BD
	@GetMapping("/ord_n")
	public String ord_n() 
	{
		return "ord_n";
	}
	
	//Añade una nueva órden a la BD
	@RequestMapping(value= "new_o", method = RequestMethod.POST)
	public String ord_n(@ModelAttribute Order order) 
	{
		
		System.out.println(order);
		ordRepo.save(order);
		
		return "ord_n";
	}

}
