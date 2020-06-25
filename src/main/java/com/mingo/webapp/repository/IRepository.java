package com.mingo.webapp.repository;


public interface IRepository<T> {
	
	Iterable<T> findAll();
	
	T findById(Long id);
	
	int save(T product);
}
