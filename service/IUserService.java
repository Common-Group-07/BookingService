package com.hotelbooking.service;

public interface IUserService {
	
	User registerUser(User user);
	
	List<User>getUsers();
	
	void deleteUser(String email);
	
	User getUser(String email);

}
