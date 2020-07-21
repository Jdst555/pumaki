package com.mingo.webapp.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
	

	@Override
	public void save(User user) {
		System.out.println("Usuario a insertar: " + user);
		jdbcTemplate.update("INSERT into user (id, name, surname, username, email, password) values (?, ?, ?, ?, ?, ?)", user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getPassword());
		jdbcTemplate.update("INSERT into authorities (username, authority) values (?, ?)", user.getUsername(), "ROLE_USER");
	}
	public void bSave(User user) 
	{
		
	}
	class UserMapper extends BeanPropertyRowMapper<User>
	{
		UserMapper()
		{
			super(User.class);
		}
	}

}
