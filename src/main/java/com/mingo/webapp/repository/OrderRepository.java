package com.mingo.webapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.mingo.webapp.model.Item;
import com.mingo.webapp.model.Order;
import com.mingo.webapp.model.Product;
import com.mingo.webapp.service.Generator;


@Repository
public class OrderRepository implements IRepository<Order> {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProductRepository prodRepo;
	

	@Override
	public Iterable<Order> findAll() {
		
		return jdbcTemplate.query("SELECT * FROM ord", new OrderMapper());
	}
	

	@Override
	public Order findById(Long id) {
		
		ArrayList<ResultProductAmount> prodAmount;
		prodAmount = (ArrayList<ResultProductAmount>) jdbcTemplate.query(new PreparedStatementCreatorImplementation(id), new RowMapperImplementation());
		ArrayList<Item> itemList = new ArrayList<>();
		for(int i = 0; i < prodAmount.size(); i++) 
		{
			Product product = prodRepo.findById(prodAmount.get(i).prod_id);
			Generator gen = new Generator();
			Item thisItem = gen.makeItemFromProduct(product, prodAmount.get(i).amount);
			itemList.add(thisItem);
		}
		
		Order thisOrder = jdbcTemplate.queryForObject("SELECT * FROM ord WHERE id=?", new OrderMapper(), id);
		thisOrder.setItemList(itemList);
		return thisOrder;
	}

	@Override
	public void save(Order order) {
		
		KeyHolder orderKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement("INSERT INTO ord ( address, active, total"
						+ ") VALUES (?, ?, ?)", new  String[] {"id"});
				ps.setString(1, order.getAddress());
				ps.setBoolean(2, order.getActive());
				ps.setFloat(3, order.getTotal());
				
				return ps;
			}
			
		}, orderKeyHolder
		
		);
		
		int key = orderKeyHolder.getKey().intValue();
		
		jdbcTemplate.update("INSERT INTO user_ord (username, ord_id) VALUES (?, ?)", 
				SecurityContextHolder.getContext().getAuthentication().getName(), key);
		for(int i = 0; i < order.getItemList().size(); i++) 
		{
			jdbcTemplate.update("INSERT INTO ord_product (ord_id, product_id, amount) VALUES (?, ?, ?)", 
					key, order.getItemList().get(i).getId(), order.getItemList().get(i).getAmount());
		}	
	}
	
	class OrderMapper extends BeanPropertyRowMapper<Order>
	{
		OrderMapper()
		{
			super(Order.class);
		}
	}
	class PreparedStatementCreatorImplementation implements PreparedStatementCreator
	{
		PreparedStatementCreatorImplementation(Long ord_id)
		{
			this.ord_id = ord_id;
		}
		
		Long ord_id;

		@Override
		public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			PreparedStatement ps = con.prepareStatement("SELECT product_id, amount FROM ord_product WHERE ord_id =?");
			ps.setLong(1, ord_id);
			return ps;
		}
	}
	class RowMapperImplementation implements RowMapper<ResultProductAmount>{

		@Override
		public ResultProductAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
			ResultProductAmount rpa = new ResultProductAmount(rs.getLong(1), rs.getInt(2));
			return rpa;
			}
		}
	class ResultProductAmount
	{
		ResultProductAmount(Long prod_id, int amount) 
		{
			this.prod_id = prod_id;
			this.amount = amount;
		}
		Long prod_id;
		int amount;
	}
	
}
