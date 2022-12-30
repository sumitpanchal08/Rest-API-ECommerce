package com.dealsnow.service;

import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CartOrderException;
import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.CartOrder;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;

public interface UserService {
	public User registerUser(User user)throws UserException;
	public String loginUser(UserDTO userDTO) throws UserException;
	public String logoutUser(String uuid) throws UserException;
	
	public Boolean checkLoginStatus(String uuid)throws AdminException;
	
    public CartOrder orderProducts(CartOrder order) throws CartOrderException;
}
