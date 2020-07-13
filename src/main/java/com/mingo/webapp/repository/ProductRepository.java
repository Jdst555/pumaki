package com.mingo.webapp.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import com.mingo.webapp.model.Product;

@Repository
public class ProductRepository implements IRepository<Product> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public Iterable<Product> findAll() {

		return jdbcTemplate.query("SELECT * FROM product", new ProductMapper());
		
	}

	@Override
	public Product findById(Long id) {
		
		return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id=?", new ProductMapper(), id);
	}

	@Override
	public void save(Product product) {
		
		jdbcTemplate.update("INSERT into product ( name, unit, price, img) values ( ?, ?, ?, ?)", product.getName(), product.getUnit(), product.getPrice(), product.getImg());
	}
	

	class ProductMapper extends BeanPropertyRowMapper<Product>
	{
		ProductMapper()
		{
			super(Product.class);
		}
	}
	
	

}
