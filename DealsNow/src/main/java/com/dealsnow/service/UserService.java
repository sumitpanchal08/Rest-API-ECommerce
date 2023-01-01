package com.dealsnow.service;

import com.dealsnow.exceptions.AddressException;
import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CartOrderException;
import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.Address;
import com.dealsnow.models.CartOrder;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;

public interface UserService {
	public User registerUser(User user)throws UserException;
	public CurrentSession loginUser(UserDTO userDTO) throws UserException;
	public String logoutUser(String uuid) throws UserException;
	public User getLoginDetails(String uuid) throws UserException;
	public Boolean checkLoginStatus(String uuid)throws UserException;
	
    public CartOrder orderProducts(CartOrder order) throws CartOrderException;
    
    public User addAddress(Address address,Integer userId)throws AddressException,UserException;
    public Address deleteAddress(Integer id)throws AddressException;
}
