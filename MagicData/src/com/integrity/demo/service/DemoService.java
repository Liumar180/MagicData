package com.integrity.demo.service;

public interface DemoService<T> {

	T find(long id);
	void save(T t);
	
}
