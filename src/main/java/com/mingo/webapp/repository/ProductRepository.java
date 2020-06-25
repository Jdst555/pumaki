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
	public int save(Product product) {
		
		return jdbcTemplate.update("INSERT into product (id, name, unit, price, img) values (?, ?, ?, ?, ?)", product.getId(), product.getName(), product.getUnit(), product.getPrice(), product.getImg());
	}
	
	//probar si mapea
	class ProductMapper extends BeanPropertyRowMapper<Product>
	{
		ProductMapper()
		{
			super(Product.class);
		}
	}
	
	

}
