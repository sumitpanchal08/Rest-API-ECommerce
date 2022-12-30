package com.dealsnow.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.CartOrderDAO;
import com.dealsnow.dao.CurrentSessionDAO;
import com.dealsnow.dao.UserDAO;
import com.dealsnow.exceptions.AddressException;
import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CartOrderException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.exceptions.UserException;
import com.dealsnow.models.Address;
import com.dealsnow.models.CartOrder;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.ProductOrderDetails;
import com.dealsnow.models.Promocode;
import com.dealsnow.models.User;
import com.dealsnow.models.UserDTO;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userdao;
	
	@Autowired
	private CurrentSessionDAO csdao;
	
	@Autowired
	private CartOrderDAO cartOrderDAO;

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
	public String loginUser(UserDTO userDTO) throws UserException {
		// TODO Auto-generated method stub
		User user=userdao.loginUser(userDTO.getMoblie(), userDTO.getPassword());
		if(user==null) {
			throw new UserException("Mobile and Password not match!!");
		}
		Optional<CurrentSession> cs=csdao.findById(user.getUserId());
		if(cs.isPresent()) {
			throw new UserException("User already login!!");
		}
        String key=RandomString.make(6);
        CurrentSession cs1=csdao.findByUuid(key);
		if(cs1!=null) {
			throw new UserException("Login Failed try Again!!");
		}
		CurrentSession cs2=new CurrentSession(user.getUserId(),key,LocalDateTime.now(),false);
		csdao.save(cs2);
		return cs2.getUuid();
	}

	@Override
	public String logoutUser(String uuid) throws UserException {
		CurrentSession cs=csdao.findByUuid(uuid);
		if(cs==null) {
			throw new UserException("User not login with this id");
		}
		csdao.delete(cs);
		return "Logout Successfull!!";
	}

	@Override
	public CartOrder orderProducts(CartOrder order) throws CartOrderException {
		// TODO Auto-generated method stub
		User user=order.getUser();
		if(user==null) {
			throw new UserException("User Id not match");
		}
		Set<Address> addresses=user.getAddresses();
		boolean flag=false;
		for(Address a:addresses) {
			if(a.getAddressId()==order.getAddress().getAddressId()) {
				flag=true;
				break;
			}
		}
		if(!flag) {
			throw new AddressException("Address is not match with the User");
		}
		Promocode pc=order.getPromocode();
		double total=0;
		order.setOrderDateTime(LocalDateTime.now());
		order.setOrderStatus("PENDING");
		
		Set<ProductOrderDetails> details =order.getProductOrderDetails();
		for(ProductOrderDetails d:details) {
			total=total+(d.getAmount()*d.getQuantity());
		}
		if(pc.getAmt()>total) {
			throw new PromocodeException("Promocode Not Valid for these Items");
		}
		total=total-pc.getAmt();
		order.setTotalamount(total);
		return cartOrderDAO.save(order);
	}

	@Override
	public Boolean checkLoginStatus(String uuid) throws AdminException {
		// TODO Auto-generated method stub
		CurrentSession cs=csdao.findByUuid(uuid);
		if(cs==null) {
			throw new AdminException("User Not Logged In with this number");
		}
		if(cs.getType()!=false) {
			throw new AdminException("User Not Logged In with this number");
		}
		return true;
	}
}
