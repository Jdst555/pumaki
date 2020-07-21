package com.mingo.webapp.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


import com.mingo.webapp.repository.OrderRepository;
import com.mingo.webapp.repository.ProductRepository;
import com.mingo.webapp.repository.UserRepository;
import com.mingo.webapp.model.Order;
import com.mingo.webapp.model.Product;
import com.mingo.webapp.model.User;

@Controller
public class Control {
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private OrderRepository ordRepo;
	
	@Autowired
	private UserRepository userRepo;
	
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
	@GetMapping("/checkout")
	public String checkout(Model model)
	{
		return "checkout";
	}
	
	/*Registra una nueva orden*/
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> buy(@ModelAttribute Order order, @RequestParam String lista)
	{
		
		System.out.println(order);
		System.out.println(lista);
		

		
		ordRepo.save(order);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	/**************Mapeo de URLs administrativas(solo rol ADMIN)*************/
	
	
	//Recupera los productos registrados en la BD y los presenta una tabla
	@GetMapping("/products")
	public String products(Model model) 
	{
		List<Product> products = (List<Product>)prodRepo.findAll();
		model.addAttribute("products", products);
		return "products";
		
	}
	
	//Presenta una interface para añadir un nuevo producto a la BD
	@GetMapping("/nuevo_producto")
	public String nuevoProducto() 
	{
		return "nuevo_producto";
	}
	
	//Añade un nuevo producto a la BD.
	@RequestMapping(value = "/registrar_nuevo_producto", method = RequestMethod.POST)
	public String registrarNuevoProducto(@ModelAttribute Product product, Model model) 
	{
		
		
		prodRepo.save(product);
		List<Product> products = (List<Product>)prodRepo.findAll();
		model.addAttribute("products", products);
		return "products";
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
	
	public String detail(@RequestParam String id, Model model) 
	{
		
		return "detail";
	}
	
	//Presenta una interface para añadir manualmente una nueva orden a la BD
	@GetMapping("/ord_n")
	public String ord_n() 
	{
		return "ord_n";
	}
	
	//Añade una nueva órden a la BD
	@RequestMapping(value= "new_o", method = RequestMethod.POST)
	public String ord_n(@ModelAttribute Order order, Model model) 
	{
		
		System.out.println(order);
		ordRepo.save(order);
		List<Order> orders = (List<Order>)ordRepo.findAll();
		model.addAttribute("orders", orders);
		return "orders";
	}
	
	@GetMapping("/user")
	public ResponseEntity<String> user()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String string = principal.toString();
		return new ResponseEntity<>(string, HttpStatus.OK);
	}
	@GetMapping("/users")
	public String users(Model model)
	{
		User user = userRepo.findByUsername("gali");
		ArrayList<User> users = new ArrayList<>();
		users.add(user);
		model.addAttribute("users", users);
		return "users";
	}

}
