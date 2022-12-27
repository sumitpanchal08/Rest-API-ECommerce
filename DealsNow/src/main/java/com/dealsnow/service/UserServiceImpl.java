package com.dealsnow.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.CurrentSessionDAO;
import com.dealsnow.dao.UserDAO;
import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userdao;
	
	@Autowired
	private CurrentSessionDAO csdao;

	@Override
	public User registerUser(User user) throws UserException {
		// TODO Auto-generated method stub
		User u=userdao.findByMobile(user.getMobile());
		if(u!=null) {
			throw new UserException("Mobile already registered try using new mobile!!!");
		}
	    user.setLastmodified(LocalDateTime.now());
	    user.setRegisterDateTime(LocalDateTime.now());
	    User user2=userdao.save(user);
		return user2;
	}

	@Override
	public User loginUser(UserDTO userDTO) throws UserException {
		// TODO Auto-generated method stub
		User user=userdao.loginUser(userDTO.getMoblie(), userDTO.getPassword());
		if(user==null) {
			throw new UserException("Mobile and Password not match!!");
		}
		Optional<CurrentSession> cs=csdao.findById(user.getUserId());
		if(cs!=null) {
			throw new UserException("User already login!!");
		}
        String key=RandomString.make(6);
		
		CurrentSession cs1=new CurrentSession(user.getUserId(),key,LocalDateTime.now());
		csdao.save(cs1);
		return user;
	}

	@Override
	public String logoutUser(Integer userId) throws UserException {
		Optional<CurrentSession> cs=csdao.findById(userId);
		if(cs==null) {
			throw new UserException("User not login with this id");
		}
		csdao.delete(cs.get());
		return "Logout Successfull!!";
	}
}
