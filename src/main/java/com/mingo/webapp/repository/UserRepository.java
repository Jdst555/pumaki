package com.mingo.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mingo.webapp.model.User;


public class UserRepository implements IRepository<User> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Iterable<User> findAll() {
		
		return jdbcTemplate.query("SELECT * FROM user", new UserMapper());
	}

	@Override
	public User findById(Long id) {
		
		return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id=?", new UserMapper(), id);
	}

	@Override
	public int save(User user) {
		return jdbcTemplate.update("INSERT into user (id, name, surname, email, password) values (?, ?, ?, ?, ?)", user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword());
	}
	
	class UserMapper extends BeanPropertyRowMapper<User>
	{
		UserMapper()
		{
			super();
		}
	}

}
