package com.mingo.webapp.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mingo.webapp.model.Order;

@Repository
public class OrderRepository implements IRepository<Order> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

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
		jdbcTemplate.update("INSERT INTO ord (id, date, address, active) values (?, ?, ?, ?)",
				order.getId(), order.getDate(), order.getAddress(), order.getActive());
	}
	
	
	class OrderMapper extends BeanPropertyRowMapper<Order>
	{
		OrderMapper()
		{
			super(Order.class);
		}
	}

}
