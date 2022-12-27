package com.dealsnow.service;

import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;

public interface UserService {
	public User registerUser(User user)throws UserException;
	public User loginUser(UserDTO userDTO) throws UserException;
	public String logoutUser(Integer userId) throws UserException;
}
