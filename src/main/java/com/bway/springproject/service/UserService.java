package com.bway.springproject.service;

import com.bway.springproject.model.User;

public interface UserService {
	
	void userSignup(User user);
	
	User userLogin(String email, String psw);

}
