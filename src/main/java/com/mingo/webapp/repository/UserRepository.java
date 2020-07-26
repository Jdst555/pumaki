package com.mingo.webapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mingo.webapp.model.User;

@Repository
public class UserRepository implements IRepository<User> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Iterable<User> findAll() {
		
		Iterable<User> users =  jdbcTemplate.query("SELECT * FROM user", new UserMapper());
		return users;
	}

	@Override
	public User findById(Long id) {
		
		return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id=?", new UserMapper(), id);
	}
	public User findByUsername(String username) {
		
		User user;
		user =  jdbcTemplate.queryForObject("SELECT * FROM user WHERE username=?", new UserMapper(), username);
		return user;
	}
	
	public User findFromOrderId(Long order_id) 
	{
		String clientUsername = "";
		clientUsername = jdbcTemplate.queryForObject("SELECT username FROM user_ord WHERE ord_id =?", new Object[] {order_id}, new SimpleUsernameMapper());
		System.out.println(clientUsername);
		User user = jdbcTemplate.queryForObject("SELECT user.id, user.name, user.surname, user.username, "
				+ "user.email, user.phone FROM user, user_ord WHERE user_ord.username =? "
				+ "and user_ord.username = user.username and user_ord.ord_id =?;", 
				new Object[]{clientUsername, order_id}, new UserMapper());
		return user;
	}
	@Override
	public void save(User user) {
		
		jdbcTemplate.update("INSERT into user (id, name, surname, username, email, password, phone) values (?, ?, ?, ?, ?, ?, ?)", user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getPassword(), user.getPhone());
		jdbcTemplate.update("INSERT into authorities (username, authority) values (?, ?)", user.getUsername(), "ROLE_USER");
	}
	
	class UserMapper extends BeanPropertyRowMapper<User>
	{
		UserMapper()
		{
			super(User.class);
		}
	}
	class SimpleUsernameMapper implements RowMapper<String>
	{

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("username");
			return username;
		}
		
	}

}
