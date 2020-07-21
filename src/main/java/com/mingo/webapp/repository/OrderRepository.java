package com.mingo.webapp.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.mingo.webapp.model.Order;
import com.mingo.webapp.model.User;

@Repository
public class OrderRepository implements IRepository<Order> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserRepository userRepo;
	

	@Override
	public Iterable<Order> findAll() {
		
		return jdbcTemplate.query("SELECT * FROM ord", new OrderMapper());
	}

	@Override
	public Order findById(Long id) {
		
		return jdbcTemplate.queryForObject("SELECT * FROM ord WHERE id=?", new OrderMapper(), id);
	}

	@Override
	public void save(Order order) {
		
		order.setDate(new Date());
		jdbcTemplate.update("INSERT INTO ord (id, date, address, active, total) values (?, ?, ?, ?, ?)",
				order.getId(), order.getDate(), order.getAddress(), order.getActive(), order.getTotal());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String string = principal.toString();
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		
		
		/*
		User user = userRepo.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		jdbcTemplate.update("INSERT INTO user_ord (user_id, ord_id) values (?, ?)", )*/
	}
	
	
	class OrderMapper extends BeanPropertyRowMapper<Order>
	{
		OrderMapper()
		{
			super(Order.class);
		}
	}

}
