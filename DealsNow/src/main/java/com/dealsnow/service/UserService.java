package com.dealsnow.service;

import java.util.List;

import com.dealsnow.exceptions.AddressException;
import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CartOrderException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.Address;
import com.dealsnow.models.CartOrder;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.ProductOrderDetails;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;

public interface UserService {
	public User registerUser(User user)throws UserException;
	public CurrentSession loginUser(UserDTO userDTO) throws UserException;
	public String logoutUser(String uuid) throws UserException;
	public User getLoginDetails(String uuid) throws UserException;
	public Boolean checkLoginStatus(String uuid)throws UserException;
	public List<User> getAllUsers()throws UserException;
	
	public CartOrder createOrder(Integer userId)throws UserException;
    public CartOrder addToCart(ProductOrderDetails p,Integer oid,Integer pid) throws CartOrderException;
    public CartOrder removeFromCart(Integer oid,Integer podid) throws CartOrderException;
    public CartOrder applyPromo(Integer oid,String code)throws PromocodeException;
    public CartOrder removePromo(Integer oid)throws CartOrderException;
    public CartOrder addAddress(Integer oid,Integer addressId)throws AddressException,CartOrderException;
    public CartOrder confirmOrder(Integer oid,Integer userId)throws UserException,CartOrderException;
    
    
    public User addAddress(Address address,Integer userId)throws AddressException,UserException;
    public Address deleteAddress(Integer id)throws AddressException;
}
